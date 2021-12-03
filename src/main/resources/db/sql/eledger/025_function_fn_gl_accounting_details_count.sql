CREATE OR REPLACE FUNCTION eledger.fn_gl_accounting_details_count(p_tpn character varying,
                                                                  p_language_code character varying,
                                                                  p_tax_type_code character varying,
                                                                  p_year character varying, p_segment character varying,
                                                                  p_start_date date, p_end_date date)
    RETURNS bigint
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val bigint;
BEGIN
    SELECT count(id)
    INTO v_ret_val
    FROM (
             SELECT t.period_year
                  , t.period_segment
                  , t.tpn
                  , t.id
                  , t.transaction_date
             FROM (
                      SELECT --a.id, a.parent_id,
                          a.id
                           , a.transaction_date
                           , tp.tpn
                           , eledger.fn_get_attribute_value(a.transaction_id, 'period_year')    AS "period_year"
                           , eledger.fn_get_attribute_value(a.transaction_id, 'period_segment') AS "period_segment"
                      FROM eledger.el_accounting a
                               INNER JOIN eledger.el_transaction t
                                          ON t.id = a.transaction_id
                               INNER JOIN eledger.el_taxpayer tp
                                          ON tp.id = t.taxpayer_id
                               LEFT JOIN eledger_config.el_gl_account ga
                                         ON ga.id = a.gl_account_id
                               LEFT JOIN eledger_config.el_gl_account_description gad
                                         ON gad.gl_account_id = a.gl_account_id AND gad.language_code = 'en'
                               LEFT JOIN eledger_config.el_accounting_action_type tat
                                         ON tat.id = a.accounting_action_type_id
                      WHERE tp.tpn = p_tpn
                        AND ga.code LIKE coalesce(p_tax_type_code, ga.code) || '%'
                        AND lower(trim(gad.language_code)) = lower(trim(p_language_code))
                        AND a.account_type = 'A'
                  ) t
             WHERE t.period_year = coalesce(p_year, t.period_year)
               AND t.period_segment = coalesce(p_segment, t.period_segment)
         ) ret
    WHERE ret.transaction_date >= coalesce(p_start_date, ret.transaction_date)
      AND ret.transaction_date <= coalesce(p_end_date, ret.transaction_date);

    RETURN coalesce(v_ret_val, 0);
END;
$function$
;
