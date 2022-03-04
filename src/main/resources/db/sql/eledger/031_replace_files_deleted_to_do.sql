

-------------------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION eledger.fn_taxpayer_account_ssp_report(p_tpn character varying, p_tax_type_code character varying, p_year character varying, p_segment character varying, p_language_code character varying)
    RETURNS TABLE(row_type character varying, transaction_date character varying, description character varying, debit numeric, credit numeric, balance numeric, drn character varying)
    LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY
        WITH acc_bal AS (
            SELECT id
                 , transaction_year
                 , transaction_segment
                 , period_description
                 , r.debit
                 , r.credit
                 , SUM(
                   (CASE WHEN r.debit IS NULL THEN 0 ELSE r.debit END)
                       -
                   (CASE WHEN r.credit IS NULL THEN 0 ELSE r.credit END)
                ) OVER (PARTITION BY transaction_year, transaction_segment ORDER BY id) balance
                 , MAX(id) OVER (PARTITION BY transaction_year, transaction_segment)    m_id
                 , parent_id
                 , gl_account_id
                 , transfer_type
                 , account_type
                 , accounting_action_type_id
                 --select r.*
            FROM (
                     SELECT t.id
                          , t.transaction_year
                          , t.transaction_segment
                          , t.transaction_date::varchar AS period_description
                          , CASE
                                WHEN t.accounting_action_type_id IN (4) THEN amount
                                WHEN t.accounting_action_type_id IN (5) and account_type = 'A' THEN amount
                                WHEN t.accounting_action_type_id IN (1) and account_type = 'P' THEN amount
                                ELSE NULL::numeric END     credit
                          , CASE
                                WHEN t.accounting_action_type_id IN (1, 2, 3) and account_type = 'A' THEN amount
                                WHEN t.accounting_action_type_id IN (5) and account_type = 'P' THEN amount
                                WHEN t.accounting_action_type_id IN (6) and account_type = 'P' THEN amount
                                ELSE NULL::numeric END     debit
                          , t.parent_id
                          , t.gl_account_id
                          , t.transfer_type
                          , t.account_type
                          , t.accounting_action_type_id
                     FROM (
                              SELECT ea.*
                                   , eledger.fn_get_attribute_value(et.id, 'PERIOD_YEAR')    transaction_year
                                   , eledger.fn_get_attribute_value(et.id, 'PERIOD_SEGMENT') transaction_segment
                              FROM eledger.el_taxpayer tp
                                       INNER JOIN eledger.el_transaction et
                                                  ON et.taxpayer_id = tp.id
                                       INNER JOIN eledger.el_accounting ea
                                                  ON ea.transaction_id = et.id
                                       INNER JOIN eledger_config.el_gl_account ega
                                                  ON ega.id = ea.gl_account_id
                              WHERE tp.tpn = p_tpn
                                and ea.amount <> 0
                                AND ega.code LIKE COALESCE(p_tax_type_code, ega.code) || '%'
                                AND (
                                          accounting_action_type_id IN (2, 3)
                                      OR
                                          (accounting_action_type_id = 1 AND account_type = 'A' AND transfer_type = 'D' and
                                           et.transaction_type_id = 1)
                                      OR
                                          (accounting_action_type_id = 1 AND account_type = 'P' AND transfer_type = 'C' and
                                           et.transaction_type_id = 2)
                                      OR
                                          (accounting_action_type_id = 1 AND account_type = 'P' AND transfer_type = 'C' and
                                           et.transaction_type_id = 4)
                                      OR
                                          (accounting_action_type_id = 4 AND account_type = 'A' AND transfer_type = 'C')
                                      OR
                                          (accounting_action_type_id = 5)
                                      OR
                                          (accounting_action_type_id = 6 AND account_type = 'P' AND transfer_type = 'D')
                                  )
                          ) t
                     WHERE transaction_year = COALESCE(p_year, transaction_year)
                       AND transaction_segment = COALESCE(p_segment, transaction_segment)
                 ) r
        )

        SELECT ret.row_type::varchar
             , CASE
                   WHEN ret.row_type = 'HEADER' THEN eledger.fn_get_period_description(transaction_year,
                                                                                       transaction_segment,
                                                                                       p_language_code)
                   ELSE period_description END period_description
             , CASE
                   WHEN ret.row_type = 'BODY' THEN eledger.fn_get_accounting_descrption(id, p_language_code)
                   ELSE '' END AS              description
             , ret.debit
             , ret.credit
             , ret.balance
             , ret.drn
             --, id, parent_id, gl_account_id, transfer_type, account_type, accounting_action_type_id
        FROM (
                 SELECT 'HEADER'      AS row_type
                      , p.id
                      , bal.transaction_year
                      , bal.transaction_segment
                      , NULL::varchar    period_description
                      , NULL::numeric    debit
                      , NULL::numeric    credit
                      , bal.balance
                      , NULL::varchar AS drn
                      , parent_id
                      , gl_account_id
                      , transfer_type
                      , account_type
                      , accounting_action_type_id
                 FROM acc_bal bal
                          INNER JOIN
                      (
                          SELECT DISTINCT 0                                                       id
                                        , eledger.fn_get_attribute_value(et.id, 'PERIOD_YEAR')    transaction_year
                                        , eledger.fn_get_attribute_value(et.id, 'PERIOD_SEGMENT') transaction_segment
                                        --, et.transaction_type_id
                          FROM eledger.el_taxpayer tp
                                   INNER JOIN eledger.el_transaction et
                                              ON et.taxpayer_id = tp.id
                          WHERE tp.tpn = p_tpn
                            AND et.transaction_type_id <> 3
                      ) p
                      ON bal.transaction_year = p.transaction_year AND bal.transaction_segment = p.transaction_segment
                 WHERE bal.id = bal.m_id

                 UNION ALL

                 SELECT 'BODY' AS                                      row_type
                      , id
                      , transaction_year
                      , transaction_segment
                      , period_description
                      , bal.debit
                      , bal.credit
                      , bal.balance
                      , CASE
                            WHEN accounting_action_type_id = 4 THEN eledger.fn_get_accounting_drn(parent_id)
                            WHEN accounting_action_type_id = 5 THEN eledger.fn_get_accounting_drn(parent_id)
                            WHEN accounting_action_type_id = 6 THEN eledger.fn_get_accounting_drn(parent_id)
                            ELSE eledger.fn_get_accounting_drn(id) END drn
                      , parent_id
                      , gl_account_id
                      , transfer_type
                      , account_type
                      , accounting_action_type_id
                 FROM acc_bal bal
             ) ret
        ORDER BY ret.transaction_year, ret.transaction_segment, ret.id;

END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE eledger.sp_repayment_from_net_negative(p_taxpayer_id bigint, p_calculation_date date)
    LANGUAGE plpgsql
AS $procedure$
DECLARE
    v_taxpayer_id                 bigint;
    v_action_type_id              int;
    v_debits                      record;
    v_credits                     record;
    v_debit_balance               decimal = 0;
    v_credit_balance              decimal = 0;
    v_repay_amount                decimal = 0;
    v_accounting_id               bigint;
    v_debit_current_accounting_id bigint;
BEGIN

    SELECT id INTO v_action_type_id FROM eledger_config.el_accounting_action_type WHERE name = 'REPAY_NET_NEGATIVE';

    --	select * from eledger.fn_transaction_balance(1, current_date) p;
    FOR v_debits IN
        SELECT d.*
        FROM eledger.fn_transaction_balance(p_taxpayer_id, p_calculation_date) d
                 INNER JOIN eledger.el_transaction et
                            ON et.id = d.transaction_id
        WHERE d.balance <> 0
          AND d.account_type = 'P'
        ORDER BY et.settlement_date, et.creation_date_time, et.id
        LOOP
            v_debit_balance := (-1) * v_debits.balance;

            --v_debit_current_accounting_id := v_debits.current_accounting_id;
            --RAISE NOTICE '%', 'v_debit_current_accounting_id := ' || v_debit_current_accounting_id::varchar;

            FOR v_credits IN
                SELECT c.*
                FROM eledger.fn_transaction_balance(p_taxpayer_id, p_calculation_date) c
                         INNER JOIN eledger.el_transaction et
                                    ON et.id = c.transaction_id
                         INNER JOIN eledger_config.el_transaction_type_gl_account c_ettga
                                    ON c_ettga.transaction_type_id = et.transaction_type_id AND
                                       c_ettga.gl_account_id = c.gl_account_id
                         INNER JOIN eledger_config.el_transaction_couples etc
                                    ON etc.to_transaction_type_account_id = c_ettga.id
                         INNER JOIN eledger_config.el_transaction_type_gl_account d_ettga
                                    ON d_ettga.id = etc.from_transaction_type_account_id
                         INNER JOIN eledger_config.el_gl_account ega
                                    ON ega.id = c.gl_account_id
                WHERE c.balance <> 0
                  AND c.account_type = 'A'
                  AND d_ettga.transaction_type_id = v_debits.transaction_type_id
                  AND d_ettga.gl_account_id = v_debits.gl_account_id
                  AND etc.accounting_action_type_id = 5
                ORDER BY eledger.fn_get_attribute_value(et.id, 'period_year')
                       , eledger.fn_get_attribute_value(et.id, 'period_segment')
                       , case
                             when substring(ega.code, 6, 6) = '990001' then 1
                             when substring(ega.code, 6, 6) = '990002' then 2
                             else 0
                    end
                       , ega.code
                       , et.settlement_date
                       , et.creation_date_time, et.id
                LOOP
                    v_credit_balance := v_credits.balance;
                    --	select * from eledger.el_accounting ea where ea.transaction_id in (1, 3) order by 1
                    --	select * from eledger_config.el_accounting_action_type eaat
                    v_repay_amount := least(v_debit_balance, v_credit_balance);
                    IF v_repay_amount = 0
                    THEN
                        Exit;
                    END IF;

                    v_accounting_id := nextval('eledger.el_accounting_id_seq');
                    INSERT INTO eledger.el_accounting(id, parent_id, transaction_id, gl_account_id, transfer_type,
                                                      account_type, amount, accounting_action_type_id, transaction_date)
                    VALUES (v_accounting_id, v_credits.current_accounting_id, v_debits.transaction_id,
                            v_debits.gl_account_id, 'D', 'P', v_repay_amount, 5, p_calculation_date);

                    v_debit_current_accounting_id := v_accounting_id;
                    v_accounting_id := nextval('eledger.el_accounting_id_seq');
                    INSERT INTO eledger.el_accounting(id, parent_id, transaction_id, gl_account_id, transfer_type,
                                                      account_type, amount, accounting_action_type_id, transaction_date)
                    VALUES (v_accounting_id, v_debit_current_accounting_id, v_credits.transaction_id,
                            v_credits.gl_account_id, 'C', 'A', v_repay_amount, 5, p_calculation_date);

                    v_debit_current_accounting_id := v_accounting_id;

                    v_debit_balance = v_debit_balance - v_repay_amount;

                END LOOP;
        END LOOP;

END;
$procedure$
;

-------------------------------------------------------------------------------------------------------------------------


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

            -- refund from net_negative
            IF v_is_document_transaction_date
            THEN
                CALL eledger.sp_refund_from_net_negative(v_taxpayer_id, v_processing_date);
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
