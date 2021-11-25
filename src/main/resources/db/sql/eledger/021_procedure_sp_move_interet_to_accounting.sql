CREATE OR REPLACE PROCEDURE eledger.sp_move_interet_to_accounting(IN p_taxpayer_id bigint, IN p_calculation_date date)
    LANGUAGE plpgsql
AS
$procedure$
DECLARE
BEGIN

    --raise notice '%', 'start procedure => sp_move_interet_to_transactions';

    INSERT INTO eledger.el_accounting (id, parent_id, transaction_id, gl_account_id, transfer_type, account_type,
                                       amount, accounting_action_type_id, transaction_date)
    SELECT nextval('eledger.el_accounting_id_seq') id
         , i.current_accounting_id
         , i.transaction_id
         , i.gl_account_id
         , 'D'                                     transfer_type
         , 'A'                                     action_type
         , sum(i.amount)
         , i.accounting_action_type_id
         , p_calculation_date
    FROM eledger.el_calculated_interest_info i
             INNER JOIN eledger.el_transaction t
                        ON t.id = i.transaction_id
    WHERE t.taxpayer_id = p_taxpayer_id
      AND i.orig_calculation_date IS NULL
      AND i.calculation_date <= p_calculation_date
    GROUP BY i.current_accounting_id, i.transaction_id, i.gl_account_id, i.accounting_action_type_id
    HAVING sum(i.amount) <> 0;

    UPDATE eledger.el_calculated_interest_info i
    SET orig_calculation_date = p_calculation_date
    WHERE i.transaction_id IN (SELECT t.id FROM eledger.el_transaction t WHERE t.taxpayer_id = p_taxpayer_id)
      AND orig_calculation_date IS NULL;

    --raise notice '%', 'end procedure => sp_move_interet_to_accounting';
END;
$procedure$
;