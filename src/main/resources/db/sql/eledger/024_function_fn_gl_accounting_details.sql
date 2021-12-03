CREATE OR REPLACE FUNCTION eledger.fn_gl_accounting_details(p_tpn character varying, p_language_code character varying,
                                                            p_tax_type_code character varying, p_year character varying,
                                                            p_segment character varying, p_start_date date,
                                                            p_end_date date, p_offset bigint, p_limit bigint)
    RETURNS TABLE
            (
                transaction_date       date,
                accounting_description character varying,
                period_year            character varying,
                period_segment         character varying,
                amount                 numeric,
                net_negative           numeric,
                total_liability        numeric,
                total_interest         numeric,
                total_penalty          numeric,
                payment                numeric,
                non_revenue            numeric,
                drn                    character varying,
                tpn                    character varying,
                accounting_id          bigint,
                transaction_type       character varying,
                transaction_id         bigint,
                gl_account_id          bigint,
                gl_account_code        character varying,
                action_type            character varying
            )
    LANGUAGE plpgsql
AS
$function$
BEGIN
    RETURN QUERY
        SELECT *
        FROM (
                 SELECT t.transaction_date
                      , t.liability_description
                      , t.period_year
                      , t.period_segment
                      , t.amount
                      , NULL::decimal AS                                                net_negative
                      , sum(t.liability_amount) OVER (PARTITION BY t.tpn ORDER BY t.id) total_liability
                      , sum(t.interest_amount) OVER (PARTITION BY t.tpn ORDER BY t.id)  total_interest
                      , sum(t.penalty_amount) OVER (PARTITION BY t.tpn ORDER BY t.id)   total_penalty
                      , t.payment
                      , NULL::decimal AS                                                non_revenue
                      , t.drn
                      -- other data
                      , t.tpn
                      , t.id
                      , t.transaction_name
                      , t.transaction_id
                      , t.gl_account_id
                      , t.gl_account_code
                      , t.action_type
                 FROM (
                          SELECT --a.id, a.parent_id,
                              a.id
                               , tp.tpn
                               , a.transaction_id
                               , (SELECT code
                                  FROM eledger_config.el_transaction_type ett
                                  WHERE ett.id = t.transaction_type_id)                                   transaction_name
                               , a.transaction_date
                               , gad.value                                                                liability_description
                               , eledger.fn_get_attribute_value(a.transaction_id, 'period_year')       AS "period_year"
                               , eledger.fn_get_attribute_value(a.transaction_id, 'period_segment')    AS "period_segment"
                               , CASE WHEN a.accounting_action_type_id = 4 THEN NULL ELSE a.amount END AS amount
                               , CASE WHEN a.accounting_action_type_id = 4 THEN a.amount ELSE NULL END AS payment
                               , eledger.fn_get_attribute_value(a.transaction_id, 'drn')                  drn
                               , ga.id                                                                    gl_account_id
                               , ga.code                                                                  gl_account_code
                               , tat.name                                                                 action_type
                               , CASE
                                     WHEN substring(ga.code, 6, 6) = '990001'
                                         THEN CASE WHEN a.transfer_type = 'D' THEN a.amount ELSE -a.amount END
                                     ELSE 0
                              END                                                                         interest_amount
                               , CASE
                                     WHEN substring(ga.code, 6, 6) = '990002'
                                         THEN CASE WHEN a.transfer_type = 'D' THEN a.amount ELSE -a.amount END
                                     ELSE 0
                              END                                                                         penalty_amount
                               , CASE
                                     WHEN substring(ga.code, 6, 6) NOT IN ('990001', '990002')
                                         THEN CASE WHEN a.transfer_type = 'D' THEN a.amount ELSE -a.amount END
                                     ELSE 0
                              END                                                                         liability_amount
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
          AND ret.transaction_date <= coalesce(p_end_date, ret.transaction_date)
        ORDER BY ret.id
            OFFSET p_offset
        LIMIT p_limit;

END;
$function$
;
