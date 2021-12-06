CREATE OR REPLACE FUNCTION eledger.fn_get_payment_advice_data(p_tpn character varying, p_calculation_date date,
                                                              p_drn character varying DEFAULT NULL::character varying)
    RETURNS TABLE
            (
                tpn            character varying,
                drn            character varying,
                period_year    character varying,
                period_segment character varying,
                payable_lines  json
            )
    LANGUAGE plpgsql
AS
$function$
    --declare v_calculation_date date;
BEGIN
    /*
    select least(p_calculation_date, c.calculated_date - 1) into v_calculation_date
    from eledger.el_taxpayer_calc c where c.tpn = p_tpn limit 1;
*/
    RETURN QUERY
        SELECT r.tpn
             , r.drn
             , r.period_year
             , r.period_segment
             , json_agg(
                json_build_object('transaction_id', r.transaction_id, 'deadline', r.deadline, 'amount', r.balance,
                                  'gl_account_code', r.code, 'description', r.description))
        FROM (
                 SELECT b.*
                      , a.code
                      , eledger.fn_get_attribute_value(b.transaction_id, 'period_year')    period_year
                      , eledger.fn_get_attribute_value(b.transaction_id, 'period_segment') period_segment
                      , eledger.fn_get_attribute_value(b.transaction_id, 'deadline')       deadline
                      , d.description
                 FROM (
                          SELECT u.tpn
                               , u.drn
                               , u.transaction_id
                               , u.transaction_type_id
                               , u.gl_account_id
                               , u.account_type
                               , sum(u.balance) AS balance
                          FROM (
                                   SELECT tp.tpn
                                        , t.drn
                                        , a.transaction_id
                                        , t.transaction_type_id
                                        , a.gl_account_id
                                        , a.account_type
                                        , sum(a.amount * (CASE WHEN transfer_type = 'D' THEN 1 ELSE -1 END)) balance
                                   FROM eledger.el_taxpayer tp
                                            INNER JOIN eledger.el_transaction t
                                                       ON t.taxpayer_id = tp.id
                                            INNER JOIN eledger.el_accounting a
                                                       ON a.transaction_id = t.id
                                   WHERE tp.tpn = p_tpn
                                     AND a.account_type = 'A'
                                     AND a.transaction_date <= p_calculation_date
                                   GROUP BY tp.tpn, t.drn, a.transaction_id, t.transaction_type_id, a.gl_account_id,
                                            a.account_type

                                   UNION ALL

                                   SELECT tp.tpn
                                        , t.drn
                                        , ecii.transaction_id
                                        , t.transaction_type_id
                                        , ecii.gl_account_id
                                        , 'A'              AS account_type
                                        , sum(ecii.amount) AS balance
                                   FROM eledger.el_calculated_interest_info ecii
                                            INNER JOIN eledger.el_transaction t
                                                       ON t.id = ecii.transaction_id
                                            INNER JOIN eledger.el_taxpayer tp
                                                       ON tp.id = t.taxpayer_id
                                   WHERE tp.tpn = p_tpn
                                     AND ecii.calculation_date <= p_calculation_date
                                     AND coalesce(ecii.orig_calculation_date, '99990101') > p_calculation_date
                                   GROUP BY tp.tpn, t.drn, ecii.transaction_id, t.transaction_type_id,
                                            ecii.gl_account_id
                               ) u
                          WHERE coalesce(p_drn, u.drn) = u.drn
                          GROUP BY u.tpn, u.drn, u.transaction_id, u.transaction_type_id, u.gl_account_id,
                                   u.account_type
                          HAVING sum(u.balance) <> 0
                      ) b
                          INNER JOIN (
                     SELECT gl_account_id,
                            json_agg(json_build_object('language_code', language_code, 'value', value)) AS description
                     FROM eledger_config.el_gl_account_description
                     GROUP BY gl_account_id
                 ) d
                                     ON d.gl_account_id = b.gl_account_id
                          INNER JOIN eledger_config.el_gl_account a
                                     ON a.id = d.gl_account_id
             ) r
        GROUP BY r.tpn, r.drn, r.period_year, r.period_segment;

END;
$function$
;
