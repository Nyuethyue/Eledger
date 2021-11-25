CREATE OR REPLACE FUNCTION eledger.fn_transaction_balance(p_taxpayer_id bigint, p_calculation_date date)
    RETURNS TABLE
            (
                transaction_id        bigint,
                transaction_type_id   bigint,
                gl_account_id         bigint,
                account_type          character varying,
                current_accounting_id bigint,
                balance               numeric
            )
    LANGUAGE plpgsql
AS
$function$
BEGIN
    RETURN QUERY
        SELECT a.transaction_id
             , t.transaction_type_id
             , a.gl_account_id
             , a.account_type
             , max(a.id)                                                          current_id
             , sum(a.amount * (CASE WHEN transfer_type = 'D' THEN 1 ELSE -1 END)) balance
        FROM eledger.el_accounting a
                 INNER JOIN eledger.el_transaction t
                            ON t.id = a.transaction_id
        WHERE t.taxpayer_id = p_taxpayer_id
          AND a.transaction_date <= p_calculation_date
        GROUP BY a.transaction_id, t.transaction_type_id, a.gl_account_id, a.account_type;

END;
$function$
;


CREATE OR REPLACE PROCEDURE eledger.sp_transaction_interest_info(IN p_taxpayer_id bigint, IN p_calculation_date date)
    LANGUAGE plpgsql
AS
$procedure$
DECLARE
    i                      record;
    v_query                varchar;
    v_calculation_date_str varchar(10);
BEGIN

    RAISE NOTICE '%', 'start procedure => sp_transaction_interest_info';
    v_calculation_date_str := '''' || to_char(p_calculation_date, 'yyyyMMdd') || '''';

    CREATE TEMPORARY TABLE temp_transaction_balance
    (
        transaction_id        bigint,
        transaction_type_id   bigint,
        gl_account_id         bigint,
        account_type          character varying,
        current_accounting_id bigint,
        balance               numeric
    );

    INSERT INTO temp_transaction_balance
    SELECT tb.transaction_id,
           tb.transaction_type_id,
           tb.gl_account_id,
           tb.account_type,
           current_accounting_id,
           tb.balance
    FROM eledger.fn_transaction_balance(p_taxpayer_id, p_calculation_date) tb
    WHERE tb.account_type = 'A'
      AND balance > 0;


    CREATE TEMPORARY TABLE temp_transaction_interest_info
    (
        gl_account_id             bigint,
        accounting_action_type_id bigint,
        interest_calculation_id   bigint,
        transaction_id            bigint,
        current_accounting_id     bigint,
        li_start_date             date,
        li_max_days_count         int,
        li_percent                decimal,
        li_max_amount             decimal,
        li_in_amount              decimal
    );


    FOR i IN
        SELECT tb.transaction_id
             , tb.balance
             , tcl.accounting_action_type_id
             , to_ttga.gl_account_id
             , ic.id AS interest_calculation_id
             , tb.current_accounting_id
             , ic.start_date_function
             , ic.days_count_function
             , ic.percent_function
             , ic.max_amount_function
             , ic.in_amount_function
             , ic.case_function
             --from eledger.fn_transaction_balance(p_taxpayer_id, p_calculation_date) tb
        FROM temp_transaction_balance tb
                 INNER JOIN eledger_config.el_transaction_type_gl_account ttga
                            ON ttga.transaction_type_id = tb.transaction_type_id AND
                               tb.gl_account_id = ttga.gl_account_id
                 INNER JOIN eledger_config.el_transaction_couples tcl
                            ON tcl.from_transaction_type_account_id = ttga.id
                 INNER JOIN eledger_config.el_accounting_action_type tat
                            ON tat.id = tcl.accounting_action_type_id
                 INNER JOIN eledger_config.el_accounting_action ta
                            ON ta.id = tat.accounting_action_id
                 INNER JOIN eledger_config.el_transaction_type_gl_account to_ttga
                            ON to_ttga.id = tcl.to_transaction_type_account_id
                 INNER JOIN eledger_config.el_interest_calculation ic
                            ON ic.accounting_action_type_id = tcl.accounting_action_type_id
        WHERE ta.name = 'ACCUMULATION'
          AND p_calculation_date BETWEEN ic.start_of_validity AND coalesce(ic.end_of_validity, '99990101')

        LOOP

            v_query := '
			insert into temp_transaction_interest_info(
			--insert into eledger.temp_transaction_interest_info(
				gl_account_id
				, accounting_action_type_id
				, interest_calculation_id
				, transaction_id
				, current_accounting_id
				, li_start_date
				, li_max_days_count
				, li_percent
				, li_max_amount
				, li_in_amount)

			select ' || i.gl_account_id || ', ' || i.accounting_action_type_id || ', '
                           || i.interest_calculation_id || ', ' || i.transaction_id || ', ' || i.current_accounting_id
                           || ', eledger.' || i.start_date_function || '(' || i.transaction_id || ', ' ||
                       v_calculation_date_str || ')'
                           || CASE
                                  WHEN i.days_count_function IS NULL THEN ', null'
                                  ELSE ', eledger.' || i.days_count_function || '(' || i.transaction_id || ', ' ||
                                       v_calculation_date_str || ')' END
                           || ', eledger.' || i.percent_function || '(' || i.transaction_id || ', ' ||
                       v_calculation_date_str || ')'
                           || CASE
                                  WHEN i.max_amount_function IS NULL THEN ', null'
                                  ELSE ', eledger.' || i.max_amount_function || '(' || i.transaction_id || ', ' ||
                                       v_calculation_date_str || ')' END
                           || ', eledger.' || i.in_amount_function || '(' || i.transaction_id || ', ' ||
                       v_calculation_date_str || ') ' || '
				where eledger.' || i.case_function || '(' || i.transaction_id || ', ' || v_calculation_date_str || ');';

            RAISE NOTICE '%', 'v_query := ' || v_query;
            EXECUTE v_query;

        END LOOP;

    --delete from eledger.calculated_interest_info where transaction_id in (select t.id from eledger.transaction t where t.taxpayer_id = p_taxpayer_id) and orig_calculation_date is null;

    INSERT INTO eledger.el_calculated_interest_info( id, transaction_id, gl_account_id, current_accounting_id
                                                   , interest_calculation_id
                                                   , accounting_action_type_id, amount, calculated_days
                                                   , calculation_date)

    SELECT nextval('eledger.el_calculated_interest_info_id_seq') id
         , ret.transaction_id
         , ret.gl_account_id
         , ret.current_accounting_id
         , ret.interest_calculation_id
         , ret.accounting_action_type_id
         , CASE
               WHEN ret.fine + ret.calculated_amount > ret.li_max_amount THEN ret.li_max_amount - ret.calculated_amount
               ELSE ret.fine END AS                              fine
         , ret.calculation_days
         , p_calculation_date
    FROM (
             SELECT r.transaction_id
                  , r.gl_account_id
                  , r.current_accounting_id
                  , r.interest_calculation_id
                  , r.accounting_action_type_id
                  --, round(r.li_in_amount * r.li_percent * (least((p_calculation_date - r.last_calc_date), (max_days_count - calculated_days))), 2) fine
                  , CASE
                        WHEN max_days_count IS NULL THEN
                                round(r.li_in_amount * r.li_percent, 2) * (p_calculation_date - r.last_calc_date)
                        ELSE round(r.li_in_amount * r.li_percent, 2) *
                             (least((p_calculation_date - r.last_calc_date), (max_days_count - calculated_days)))
                 END fine
                  --, least((p_calculation_date - r.last_calc_date), (max_days_count - calculated_days))
                  , CASE
                        WHEN max_days_count IS NULL THEN p_calculation_date - r.last_calc_date
                        ELSE least((p_calculation_date - r.last_calc_date), (max_days_count - calculated_days))
                 END calculation_days
                  , r.calculated_amount
                  , r.li_max_amount
             FROM (
                      SELECT t.transaction_id
                           , t.gl_account_id
                           , t.current_accounting_id
                           , t.interest_calculation_id
                           , t.accounting_action_type_id
                           , t.li_in_amount
                           , t.li_max_amount
                           , t.li_start_date
                           , t.li_max_days_count                           max_days_count
                           , t.li_percent
                           , coalesce(c.calculated_days, 0)                calculated_days
                           , coalesce(c.calculation_date, t.li_start_date) last_calc_date
                           , coalesce(c.calculated_amount, 0)              calculated_amount
                      FROM temp_transaction_interest_info t
                               LEFT JOIN (
                          SELECT transaction_id
                               , gl_account_id
                               , interest_calculation_id
                               , accounting_action_type_id
                               , sum(calculated_days)  calculated_days
                               , max(calculation_date) calculation_date
                               , sum(amount)           calculated_amount
                          FROM eledger.el_calculated_interest_info
                          GROUP BY transaction_id, gl_account_id, interest_calculation_id, accounting_action_type_id
                      ) c
                                         ON c.transaction_id = t.transaction_id
                                             AND c.gl_account_id = t.gl_account_id
                                             AND c.accounting_action_type_id = t.accounting_action_type_id
                      WHERE t.li_in_amount > 0
                  ) r
             WHERE greatest(r.li_start_date, r.last_calc_date) <= p_calculation_date
               AND CASE
                       WHEN max_days_count IS NULL THEN p_calculation_date - r.last_calc_date
                       ELSE least((p_calculation_date - r.last_calc_date), (max_days_count - calculated_days))
                       END > 0
         ) ret
    WHERE CASE
              WHEN ret.fine + ret.calculated_amount > ret.li_max_amount THEN ret.li_max_amount - ret.calculated_amount
              ELSE ret.fine END > 0;

    DROP TABLE temp_transaction_interest_info;

    --raise notice '%', 'end procedure => sp_interest_calculation';
    DROP TABLE temp_transaction_balance;

END;
$procedure$
;
