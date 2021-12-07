------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_is_transaction_date(p_taxpayer_id bigint, p_calculation_date date)
    RETURNS boolean
    LANGUAGE plpgsql
AS
$function$
BEGIN
    IF exists
        (
            SELECT 1
            FROM eledger.el_transaction t
            WHERE t.taxpayer_id = p_taxpayer_id
              AND settlement_date = p_calculation_date
        )
    THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$function$
;

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE eledger.sp_process_pac_for_date(IN p_tpn character varying, IN p_calculation_date date)
    LANGUAGE plpgsql
AS
$procedure$
DECLARE
    v_processing_date              date;
    v_orig_processing_date         date;
    v_rows_affected                int    := 0;
    v_is_document_transaction_date bool   := FALSE;
    v_taxpayer_id                  bigint := 0;
    v_is_calculaited_tpn           int    := 1;
BEGIN
    --raise notice '%', 'start procedure => sp_process_pac_for_date';
    --raise notice '%', 'p_tpn := ' || p_tpn_id::text;
    --raise notice '%', 'p_calculation_date := ' || to_char(p_calculation_date, 'yyyy-mm-dd');

    IF NOT exists(SELECT 1 FROM eledger.el_taxpayer_calc WHERE tpn = p_tpn) THEN

        INSERT INTO eledger.el_taxpayer_calc(id, tpn, creation_date_time)
        SELECT id, tpn, creation_date_time
        FROM eledger.el_taxpayer
        WHERE tpn = p_tpn;

    END IF;


    SELECT id INTO v_taxpayer_id FROM eledger.el_taxpayer WHERE tpn = p_tpn;

    SELECT t.calculated_date
    INTO v_processing_date
    FROM eledger.el_taxpayer_calc t
    WHERE t.id = v_taxpayer_id
    LIMIT 1;

    --RAISE NOTICE '%', 'v_processing_date := ' || v_processing_date::text;
    IF v_processing_date IS NULL
    THEN
        SELECT min(cast(eledger.fn_get_attribute_value(t.id, 'settlement_date') AS date))
        INTO v_processing_date
        FROM eledger.el_transaction t
        WHERE t.taxpayer_id = v_taxpayer_id;
    END IF;

    --RAISE NOTICE '%', 'v_processing_date := ' || to_char(v_processing_date, 'yyyy-mm-dd');

    WHILE v_processing_date <= p_calculation_date
        LOOP

            --raise notice '%', 'v_processing_date_loop := ' || to_char(v_processing_date, 'yyyy-mm-dd');
            v_is_document_transaction_date := eledger.fn_is_transaction_date(v_taxpayer_id, v_processing_date);
            --raise notice '%', 'v_is_document_transaction_date := ' || (v_is_document_transaction_date::int)::text;

            -- move interest to main table
            IF v_is_document_transaction_date
            THEN
                CALL eledger.sp_formulation_transaction(v_taxpayer_id, v_processing_date);
            END IF;

            -- calculate daily interest
            CALL eledger.sp_transaction_interest_info(v_taxpayer_id, v_processing_date);

            -- move interest to main table
            IF v_is_document_transaction_date --OR v_processing_date = p_calculation_date
            THEN
                CALL eledger.sp_move_interet_to_accounting(v_taxpayer_id, v_processing_date);
            END IF;

            -- repayment documents
            IF v_is_document_transaction_date
            THEN
                CALL eledger.sp_repayment(v_taxpayer_id, v_processing_date);
            END IF;

            -- repayment documents from other debits
            IF v_is_document_transaction_date
            THEN
                CALL eledger.sp_repayment_from_net_negative(v_taxpayer_id, v_processing_date);
            END IF;


            -- update taxpayer calculation_date
            IF v_is_document_transaction_date OR v_processing_date = p_calculation_date
            THEN
                CALL eledger.sp_update_taxpayer_calculation_date(v_taxpayer_id, v_processing_date);
            END IF;

            v_processing_date := v_processing_date + 1;

        END LOOP;

    --raise notice '%', 'end procedure => sp_process_pac_for_date';

END;
$procedure$
;

------------------------------------------------------------------------------------------------------------------------


