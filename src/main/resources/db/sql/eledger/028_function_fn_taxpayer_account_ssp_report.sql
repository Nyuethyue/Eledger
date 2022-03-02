--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_accounting_drn(p_account_id bigint)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    SELECT et.drn
    INTO v_ret_val
    FROM eledger.el_accounting ea
             INNER JOIN eledger.el_transaction et
                        ON et.id = ea.transaction_id
    WHERE ea.id = p_account_id;

    RETURN COALESCE(v_ret_val, '');

END;
$function$
;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_gl_account_value(p_account_id bigint, p_language_code character varying)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    SELECT egad.value
    INTO v_ret_val
    FROM eledger.el_accounting ea
             inner join eledger_config.el_gl_account ega
                        on ega.id = ea.gl_account_id
             inner join eledger_config.el_gl_account_description egad
                        on egad.gl_account_id = ega.id
    WHERE ea.id = p_account_id
      and egad.language_code = p_language_code
    limit 1;

    RETURN COALESCE(v_ret_val, '');

END;
$function$
;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_accounting_period_description(p_account_id bigint, p_language_code character varying)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    select eledger.fn_get_period_description(eledger.fn_get_attribute_value(t.id, 'PERIOD_YEAR'),
                                             eledger.fn_get_attribute_value(t.id, 'PERIOD_SEGMENT'), p_language_code)
    into v_ret_val
    from eledger.el_accounting a
             inner join eledger.el_transaction t
                        on a.transaction_id = t.id
    where a.id = p_account_id
    limit 1;

    RETURN COALESCE(v_ret_val, '');

END;
$function$
;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_accounting_descrption(p_account_id bigint, p_language_code character varying)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    SELECT CASE
               WHEN ea.accounting_action_type_id = 1
                   THEN 'Return Filed'
               WHEN ea.accounting_action_type_id = 2 AND ea.transfer_type = 'D' AND ea.account_type = 'A'
                   THEN 'Interest assessed'
               WHEN ea.accounting_action_type_id = 3 AND ea.transfer_type = 'D' AND ea.account_type = 'A'
                   THEN 'Penalty assessed'
               WHEN ea.accounting_action_type_id = 4 THEN 'Payment Received with'
               WHEN ea.accounting_action_type_id = 5 THEN 'Net Off'
               ELSE ''
               END || ' ' ||
           CASE
               WHEN ea.accounting_action_type_id = 5 AND ea.account_type = 'P'
                   THEN eledger.fn_get_gl_account_value(ea.parent_id, p_language_code) || ' '
                   --|| eledger.fn_get_gl_account_value(ea.parent_id, p_language_code)  || ' '
                   || eledger.fn_get_accounting_period_description(ea.parent_id, p_language_code)

               WHEN ea.accounting_action_type_id = 5 AND ea.account_type = 'A'
                   THEN eledger.fn_get_gl_account_value(ea.id, p_language_code) || ' From '
                            || eledger.fn_get_gl_account_value(ea.parent_id, p_language_code) || ' '
                   || eledger.fn_get_accounting_period_description(ea.parent_id, p_language_code)

               ELSE eledger.fn_get_gl_account_value(ea.id, p_language_code) END AS description
    INTO v_ret_val
    FROM eledger.el_accounting ea
             INNER JOIN eledger_config.el_gl_account_description egad
                        ON egad.gl_account_id = ea.gl_account_id
    WHERE ea.id = p_account_id
      AND egad.language_code = p_language_code;

    RETURN COALESCE(v_ret_val, '');

END;
$function$
;


--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_period_description(p_year varchar, p_segment varchar, p_language_code varchar)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    v_ret_val := p_year ||
                 CASE p_segment
                     WHEN '1' THEN CASE WHEN p_language_code = 'en' THEN '-Jan' ELSE '' END
                     WHEN '2' THEN CASE WHEN p_language_code = 'en' THEN '-Feb' ELSE '' END
                     WHEN '3' THEN CASE WHEN p_language_code = 'en' THEN '-Mar' ELSE '' END
                     WHEN '4' THEN CASE WHEN p_language_code = 'en' THEN '-Apr' ELSE '' END
                     WHEN '5' THEN CASE WHEN p_language_code = 'en' THEN '-May' ELSE '' END
                     WHEN '6' THEN CASE WHEN p_language_code = 'en' THEN '-Jun' ELSE '' END
                     WHEN '7' THEN CASE WHEN p_language_code = 'en' THEN '-Jul' ELSE '' END
                     WHEN '8' THEN CASE WHEN p_language_code = 'en' THEN '-Aug' ELSE '' END
                     WHEN '9' THEN CASE WHEN p_language_code = 'en' THEN '-Sep' ELSE '' END
                     WHEN '10' THEN CASE WHEN p_language_code = 'en' THEN '-Oct' ELSE '' END
                     WHEN '11' THEN CASE WHEN p_language_code = 'en' THEN '-Nov' ELSE '' END
                     WHEN '12' THEN CASE WHEN p_language_code = 'en' THEN '-Dec' ELSE '' END
                     END;

    RETURN COALESCE(v_ret_val, '');

END;
$function$
;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_taxpayer_account_ssp_report(p_tpn character varying,
                                                                  p_tax_type_code character varying,
                                                                  p_year character varying, p_segment character varying,
                                                                  p_language_code character varying)
    RETURNS TABLE
            (
                row_type         character varying,
                transaction_date character varying,
                description      character varying,
                debit            numeric,
                credit           numeric,
                balance          numeric,
                drn              character varying
            )
    LANGUAGE plpgsql
AS
$function$
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
                                AND ea.amount <> 0
                                AND ega.code LIKE COALESCE(p_tax_type_code, ega.code) || '%'
                                AND (
                                      accounting_action_type_id IN (2, 3)
                                      OR
                                      (accounting_action_type_id = 1 AND account_type = 'A' AND transfer_type = 'D')
                                      OR
                                      (accounting_action_type_id = 1 AND account_type = 'P' AND transfer_type = 'C' and
                                       et.transaction_type_id = 2)
                                      OR
                                      (accounting_action_type_id = 4 AND account_type = 'A' AND transfer_type = 'C')
                                      OR
                                      (accounting_action_type_id = 5)
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


--------------------------------------------------------------------------------------------------
