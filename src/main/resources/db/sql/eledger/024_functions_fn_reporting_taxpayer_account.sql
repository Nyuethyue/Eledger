----------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_reporting_taxpayer_account_count(p_tpn character varying,
                                                                       p_language_code character varying,
                                                                       p_tax_type_code character varying,
                                                                       p_year character varying,
                                                                       p_segment character varying,
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
                        and not (t.transaction_type_id = 3 and a.account_type = 'P')
                        and not (a.accounting_action_type_id = 1 and t.transaction_type_id = 5)
                        and not (a.accounting_action_type_id = 6 and a.transfer_type = 'C' and a.account_type = 'A')

                      UNION ALL

                      SELECT a.id
                           , a.transaction_date
                           , tp.tpn
                           , eledger.fn_get_attribute_value(a.transaction_id, 'period_year')    AS "period_year"
                           , eledger.fn_get_attribute_value(a.transaction_id, 'period_segment') AS "period_segment"
                      FROM (
                               SELECT row_number() OVER (ORDER BY ecii.gl_account_id, ecii.transaction_id) id
                                    , ecii.transaction_id
                                    , ecii.gl_account_id
                                    , ecii.accounting_action_type_id
                                    , 'D'                                                                  transfer_type
                                    , 'A'                                                                  account_type
                                    , max(calculation_date) AS                                             transaction_date
                                    , sum(ecii.amount)      AS                                             amount
                               FROM eledger.el_calculated_interest_info ecii
                               WHERE calculation_date <= coalesce(p_end_date, '99990101')
                                 AND coalesce(orig_calculation_date, '99990102') > coalesce(p_end_date, '99990101')
                               GROUP BY ecii.transaction_id, ecii.gl_account_id, ecii.accounting_action_type_id
                           ) a
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

----------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_reporting_taxpayer_account(p_tpn character varying,
                                                                   p_language_code character varying,
                                                                   p_tax_type_code character varying,
                                                                   p_year character varying,
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
                total_net_negative     numeric,
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

                      , (case
                             when t.action_type = 'REPAY_NET_NEGATIVE' then 'Net Off '
                             when t.action_type = 'NET_NEGATIVE_TO_TAXPAYER_ACCOUNT' then 'Refund '
                             else '' end || t.liability_description)::varchar as                          liability_description
                      , t.period_year
                      , t.period_segment
                      , t.amount
                      , case
                            when t.action_type::varchar = 'FORMULAIT'::varchar then t.net_negative
                            else 0 end                                        AS                          net_negative
                      , sum(t.net_negative)
                        OVER (PARTITION BY t.tpn ORDER BY t.transaction_date, t.id)                       total_net_negative
                      , sum(t.liability_amount)
                        OVER (PARTITION BY t.tpn ORDER BY t.transaction_date, t.id)                       total_liability
                      , sum(t.interest_amount)
                        OVER (PARTITION BY t.tpn ORDER BY t.transaction_date, t.id)                       total_interest
                      , sum(t.penalty_amount) OVER (PARTITION BY t.tpn ORDER BY t.transaction_date, t.id) total_penalty
                      , t.payment
                      , NULL::decimal                                         AS                          non_revenue
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
                          SELECT r.id
                               , r.tpn
                               , r.transaction_id
                               , (SELECT code
                                  FROM eledger_config.el_transaction_type ett
                                  WHERE ett.id = r.transaction_type_id)                                   transaction_name
                               , r.transaction_date
                               , r.value                                                                  liability_description
                               , eledger.fn_get_attribute_value(r.transaction_id, 'period_year')       AS "period_year"
                               , eledger.fn_get_attribute_value(r.transaction_id, 'period_segment')    AS "period_segment"
                               , CASE
                                     WHEN r.accounting_action_type_id = 4 THEN NULL
                                     WHEN r.accounting_action_type_id = 1 and r.transaction_type_id = 5 THEN NULL
                                     ELSE r.amount END                                                 AS amount
                               , CASE WHEN r.accounting_action_type_id = 4 THEN r.amount ELSE NULL END AS payment
                               , eledger.fn_get_attribute_value(r.transaction_id, 'drn')                  drn
                               , r.gl_account_id
                               , r.gl_account_code
                               , r.action_type
                               , (case
                                      when r.transaction_type_id in (2, 4)
                                          then CASE WHEN r.action_type = 'FORMULAIT' THEN r.amount ELSE -r.amount end
                                      else 0
                              end)::numeric                                                               net_negative
                               , CASE
                                     when r.transaction_type_id = 1 and substring(r.gl_account_code, 6, 6) = '990001'
                                         THEN CASE WHEN r.action_type = 'INTEREST' THEN r.amount ELSE -r.amount END
                                     ELSE 0
                              END                                                                         interest_amount
                               , CASE
                                     WHEN r.transaction_type_id = 1 and substring(r.gl_account_code, 6, 6) = '990002'
                                         THEN CASE
                                                  WHEN r.action_type = 'FINE_AND_PENALTY' THEN r.amount
                                                  ELSE -r.amount END
                                     ELSE 0
                              END                                                                         penalty_amount
                               , CASE
                                     WHEN r.transaction_type_id = 1 and
                                          substring(r.gl_account_code, 6, 6) NOT IN ('990001', '990002')
                                         THEN CASE WHEN r.transfer_type = 'D' THEN r.amount ELSE -r.amount END
                                     ELSE 0
                              END                                                                         liability_amount
                          FROM (
                                   SELECT a.id
                                        , tp.tpn
                                        , a.transaction_id
                                        , t.transaction_type_id
                                        , a.transaction_date
                                        , gad.value
                                        , a.accounting_action_type_id
                                        , a.amount
                                        , ga.id    gl_account_id
                                        , ga.code  gl_account_code
                                        , tat.name action_type
                                        , a.transfer_type
                                   FROM eledger.el_accounting a
                                            INNER JOIN eledger.el_transaction t
                                                       ON t.id = a.transaction_id
                                            INNER JOIN eledger.el_taxpayer tp
                                                       ON tp.id = t.taxpayer_id
                                            LEFT JOIN eledger_config.el_gl_account ga
                                                      ON ga.id = a.gl_account_id
                                            LEFT JOIN eledger_config.el_gl_account_description gad
                                                      ON gad.gl_account_id = a.gl_account_id AND
                                                         gad.language_code = p_language_code
                                            LEFT JOIN eledger_config.el_accounting_action_type tat
                                                      ON tat.id = a.accounting_action_type_id
                                   WHERE tp.tpn = p_tpn
                                     AND ga.code LIKE coalesce(p_tax_type_code, ga.code) || '%'
                                     AND lower(trim(gad.language_code)) = lower(trim(p_language_code))
                                     and not (t.transaction_type_id = 3 and a.account_type = 'P')
                                     and not (a.accounting_action_type_id = 1 and t.transaction_type_id = 5)
                                     and not (a.accounting_action_type_id = 6 and a.transfer_type = 'C' and
                                              a.account_type = 'A')

                                   UNION ALL

                                   SELECT a.id
                                        , tp.tpn
                                        , a.transaction_id
                                        , t.transaction_type_id
                                        , a.transaction_date
                                        , gad.value
                                        , a.accounting_action_type_id
                                        , a.amount
                                        , ga.id    gl_account_id
                                        , ga.code  gl_account_code
                                        , tat.name action_type
                                        , a.transfer_type
                                   FROM (
                                            SELECT row_number() OVER (ORDER BY ecii.gl_account_id, ecii.transaction_id) id
                                                 , ecii.transaction_id
                                                 , ecii.gl_account_id
                                                 , ecii.accounting_action_type_id
                                                 , 'D'                                                                  transfer_type
                                                 , 'A'                                                                  account_type
                                                 , max(calculation_date) AS                                             transaction_date
                                                 , sum(ecii.amount)      AS                                             amount
                                            FROM eledger.el_calculated_interest_info ecii

                                            WHERE calculation_date <= coalesce(p_end_date, '99990101')
                                              AND coalesce(orig_calculation_date, '99990102') >
                                                  coalesce(p_end_date, '99990101')

                                            GROUP BY ecii.transaction_id, ecii.gl_account_id,
                                                     ecii.accounting_action_type_id
                                        ) a
                                            INNER JOIN eledger.el_transaction t
                                                       ON t.id = a.transaction_id
                                            INNER JOIN eledger.el_taxpayer tp
                                                       ON tp.id = t.taxpayer_id
                                            LEFT JOIN eledger_config.el_gl_account ga
                                                      ON ga.id = a.gl_account_id
                                            LEFT JOIN eledger_config.el_gl_account_description gad
                                                      ON gad.gl_account_id = a.gl_account_id AND
                                                         gad.language_code = p_language_code
                                            LEFT JOIN eledger_config.el_accounting_action_type tat
                                                      ON tat.id = a.accounting_action_type_id
                                   WHERE tp.tpn = p_tpn
                                     AND ga.code LIKE coalesce(p_tax_type_code, ga.code) || '%'
                                     AND lower(trim(gad.language_code)) = lower(trim(p_language_code))
                                     and a.account_type = 'A'
                               ) r
                      ) t
                 WHERE t.period_year = coalesce(p_year, t.period_year)
                   AND t.period_segment = coalesce(p_segment, t.period_segment)
             ) ret
        WHERE ret.transaction_date >= coalesce(p_start_date, ret.transaction_date)
          AND ret.transaction_date <= coalesce(p_end_date, ret.transaction_date)
        ORDER BY ret.transaction_date, ret.id
        OFFSET p_offset LIMIT p_limit;

END;
$function$
;


----------------------------------------------------------------------------------------------------------------------------------

