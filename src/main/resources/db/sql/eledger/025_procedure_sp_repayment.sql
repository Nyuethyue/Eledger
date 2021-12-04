CREATE OR REPLACE PROCEDURE eledger.sp_repayment(IN p_taxpayer_id bigint, IN p_calculation_date date)
    LANGUAGE plpgsql
AS
$procedure$
DECLARE
    r_pay            record;
    v_taxpayer_id    bigint;
    v_action_type_id int;
BEGIN

    SELECT id INTO v_action_type_id FROM eledger_config.el_accounting_action_type WHERE name = 'PAYMENT';

    CREATE TEMPORARY TABLE temp_repayment
    (
        transaction_id     bigint,
        gl_account_id      bigint,
        accounting_id      bigint,
        account_id         bigint,
        name               varchar,
        balance            decimal,
        creation_date_time timestamp
    );

    INSERT INTO temp_repayment
    SELECT p.transaction_id,
           p.gl_account_id,
           p.current_accounting_id,
           ttga.id,
           tt.code,
           p.balance,
           tr.creation_date_time
           --select P.*, tt.name, ttga.id
    FROM eledger.el_taxpayer tp
             INNER JOIN eledger.el_transaction tr
                        ON tr.taxpayer_id = tp.id
             INNER JOIN eledger_config.el_transaction_type tt
                        ON tt.id = tr.transaction_type_id
             INNER JOIN eledger.fn_transaction_balance(p_taxpayer_id, p_calculation_date) p
                        ON p.transaction_id = tr.id
             INNER JOIN eledger_config.el_transaction_type_gl_account ttga
                        ON ttga.transaction_type_id = tr.transaction_type_id AND ttga.gl_account_id = p.gl_account_id
    WHERE tp.id = p_taxpayer_id
      AND p.balance <> 0;

    INSERT INTO eledger.el_accounting
    SELECT nextval('eledger.el_accounting_id_seq')                                          id
         , CASE WHEN tt.transfer_type = 'D' THEN l.accounting_id ELSE p.accounting_id END   accounting_id
         , CASE WHEN tt.transfer_type = 'D' THEN p.transaction_id ELSE l.transaction_id END transaction_id
         , CASE WHEN tt.transfer_type = 'D' THEN p.gl_account_id ELSE l.gl_account_id END   gl_account_id
         , tt.transfer_type                                                                 transfer_type
         , CASE WHEN tt.transfer_type = 'D' THEN 'P' ELSE 'A' END                           account_type
         , least(-p.balance, l.balance) AS                                                  balance
         , tc.accounting_action_type_id                                                     action_type_id
         , p_calculation_date
    FROM eledger_config.el_transaction_couples tc
             INNER JOIN temp_repayment p
                        ON p.account_id = tc.from_transaction_type_account_id
             INNER JOIN temp_repayment l
                        ON l.account_id = tc.to_transaction_type_account_id
             INNER JOIN (SELECT 1 rn, 'D' transfer_type UNION SELECT 2 rn, 'C' transfer_type) tt ON TRUE
    WHERE tc.accounting_action_type_id = v_action_type_id
      AND eledger.fn_get_attribute_value(p.transaction_id, 'target_transaction_id')::bigint = l.transaction_id
      AND eledger.fn_get_attribute_value(p.transaction_id, 'target_drn') =
          eledger.fn_get_attribute_value(l.transaction_id, 'drn')
    ORDER BY p.creation_date_time, p.transaction_id, tt.rn;

    DROP TABLE temp_repayment;
END;
$procedure$
;
