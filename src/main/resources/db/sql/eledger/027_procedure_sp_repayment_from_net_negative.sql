CREATE OR REPLACE PROCEDURE eledger.sp_repayment_from_net_negative(IN p_taxpayer_id bigint, IN p_calculation_date date)
    LANGUAGE plpgsql
AS
$procedure$
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

            v_debit_current_accounting_id := v_debits.current_accounting_id;
            RAISE NOTICE '%', 'v_debit_current_accounting_id := ' || v_debit_current_accounting_id::varchar;

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
                WHERE c.balance <> 0
                  AND c.account_type = 'A'
                  AND d_ettga.transaction_type_id = v_debits.transaction_type_id
                  AND d_ettga.gl_account_id = v_debits.gl_account_id
                  AND etc.accounting_action_type_id = 5
                ORDER BY et.settlement_date
                       , eledger.fn_get_attribute_value(et.id, 'period_year')
                       , eledger.fn_get_attribute_value(et.id, 'period_segment')
                       , et.creation_date_time, et.id
                LOOP
                    v_credit_balance := v_credits.balance;
                    --	select * from eledger.el_accounting ea where ea.transaction_id in (1, 3) order by 1
                    --	select * from eledger_config.el_accounting_action_type eaat
                    v_repay_amount := least(v_debit_balance, v_credit_balance);
                    IF v_repay_amount = 0
                    THEN
                        RETURN;
                    END IF;

                    v_accounting_id := nextval('eledger.el_accounting_id_seq');
                    INSERT INTO eledger.el_accounting(id, parent_id, transaction_id, gl_account_id, transfer_type,
                                                      account_type, amount, accounting_action_type_id, transaction_date)
                    VALUES (v_accounting_id, v_credits.current_accounting_id, v_debits.transaction_id,
                            v_debits.gl_account_id, 'D', 'P', v_repay_amount, 5, p_calculation_date);

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