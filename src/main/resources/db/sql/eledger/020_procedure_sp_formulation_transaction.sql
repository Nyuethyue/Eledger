CREATE OR REPLACE PROCEDURE eledger.sp_formulation_transaction(IN p_taxpapyer_id bigint, IN p_calculation_date date)
    LANGUAGE plpgsql
AS
$procedure$
DECLARE
    v_bal_account_id int;
    v_is_debit       bool;
    v_amount         decimal;
BEGIN
    CREATE TEMPORARY TABLE temp_new_transaction
    (
        id                  bigint,
        transaction_id      bigint,
        transaction_type_id bigint,
        gl_account_id       bigint,
        account_type        character varying,
        amount              numeric
    );

    INSERT INTO temp_new_transaction
    SELECT nextval('eledger.el_accounting_id_seq') accounting_id
         , t.id
         , t.transaction_type_id
         , ga.id
         , ttga.account_type
         , eledger.fn_get_attribute_value(t.id, 'amount')::decimal
    FROM eledger.el_transaction t
             INNER JOIN eledger_config.el_transaction_type_gl_account ttga
                        ON ttga.transaction_type_id = t.transaction_type_id
             INNER JOIN eledger_config.el_gl_account ga
                        ON ga.id = ttga.gl_account_id
             LEFT JOIN eledger.el_accounting a
                       ON a.transaction_id = t.id AND a.accounting_action_type_id = 1
    WHERE t.taxpayer_id = p_taxpapyer_id
      AND a.id IS NULL
      AND t.settlement_date = p_calculation_date
      AND t.gl_account_code = ga.code
    ORDER BY t.id;

    --select * from public.accounting a
    INSERT INTO eledger.el_accounting
    SELECT id
         , id                                                                                 AS parent_id
         , tnt.transaction_id
         , tnt.gl_account_id
         , CASE WHEN tnt.account_type = 'A' THEN 'D' WHEN tnt.account_type = 'P' THEN 'C' END AS transfer_type
         , tnt.account_type
         , tnt.amount
         , 1                                                                                  AS accounting_action_type_id --	to do formulaited id
         , p_calculation_date
    FROM temp_new_transaction tnt;

    DROP TABLE temp_new_transaction;

    --raise notice '%', 'end procedure => sp_formulaion_transaction';

END;
$procedure$
;
