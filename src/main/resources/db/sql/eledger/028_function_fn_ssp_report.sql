--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_accounting_drn(p_account_id bigint)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    select et.drn
    into v_ret_val
    from eledger.el_accounting ea
             inner join eledger.el_transaction et
                        on et.id = ea.transaction_id
    where ea.id = p_account_id;

    RETURN coalesce(v_ret_val, '');

END;
$function$
;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_accounting_descrption(p_account_id bigint, p_language_code varchar)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    select case
               when ea.accounting_action_type_id = 1 and ea.transfer_type = 'D' and ea.account_type = 'A'
                   then 'Return Filed'
               when ea.accounting_action_type_id = 2 and ea.transfer_type = 'D' and ea.account_type = 'A'
                   then 'Interest assessed'
               when ea.accounting_action_type_id = 3 and ea.transfer_type = 'D' and ea.account_type = 'A'
                   then 'Penalty assessed'
               when ea.accounting_action_type_id = 4 then 'Payment Received with'
               else ''
               end || ' ' || egad.value as description
    into v_ret_val
    from eledger.el_accounting ea
             inner join eledger_config.el_gl_account_description egad
                        on egad.gl_account_id = ea.gl_account_id
    where ea.id = p_account_id
      and egad.language_code = p_language_code;

    RETURN coalesce(v_ret_val, '');

END;
$function$
;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_accounting_period_description(p_year varchar, p_segment varchar, p_language_code varchar)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    v_ret_val := p_year ||
                 case p_segment
                     when '01' then case when p_language_code = 'en' then '-Jan' else '' end
                     when '02' then case when p_language_code = 'en' then '-Feb' else '' end
                     when '03' then case when p_language_code = 'en' then '-Mar' else '' end
                     when '04' then case when p_language_code = 'en' then '-Apr' else '' end
                     when '05' then case when p_language_code = 'en' then '-May' else '' end
                     when '06' then case when p_language_code = 'en' then '-Jun' else '' end
                     when '07' then case when p_language_code = 'en' then '-Jul' else '' end
                     when '08' then case when p_language_code = 'en' then '-Aug' else '' end
                     when '09' then case when p_language_code = 'en' then '-Sep' else '' end
                     when '10' then case when p_language_code = 'en' then '-Oct' else '' end
                     when '11' then case when p_language_code = 'en' then '-Nov' else '' end
                     when '12' then case when p_language_code = 'en' then '-Dec' else '' end
                     end;

    RETURN coalesce(v_ret_val, '');

END;
$function$
;

--------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_ssp_report(p_tpn varchar, p_tax_type_code varchar, p_year varchar,
                                                 p_segment varchar, p_language_code varchar)
    RETURNS TABLE
            (
                row_type         varchar,
                transaction_date varchar,
                description      varchar,
                debit            numeric,
                credit           numeric,
                balance          numeric,
                drn              varchar
            )
    LANGUAGE plpgsql
AS
$function$
BEGIN
    RETURN QUERY
        with acc_bal as (
            select id
                 , transaction_year
                 , transaction_segment
                 , period_description
                 , r.debit
                 , r.credit
                 , sum(
                   (case when r.credit is null then 0 else r.credit end)
                       -
                   (case when r.debit is null then 0 else r.debit end)
                ) over (partition by transaction_year, transaction_segment order by id) balance
                 , max(id) over (partition by transaction_year, transaction_segment)    m_id
                 , parent_id
                 , gl_account_id
                 , transfer_type
                 , account_type
                 , accounting_action_type_id
                 --select r.*
            from (
                     select t.id
                          , t.transaction_year
                          , t.transaction_segment
                          , t.transaction_date::varchar as                                                        period_description
                          , case when t.accounting_action_type_id in (4) then amount else null::numeric end       debit
                          , case when t.accounting_action_type_id in (1, 2, 3) then amount else null::numeric end credit
                          , t.parent_id
                          , t.gl_account_id
                          , t.transfer_type
                          , t.account_type
                          , t.accounting_action_type_id
                     from (
                              select ea.*
                                   , eledger.fn_get_attribute_value(et.id, 'PERIOD_YEAR')    transaction_year
                                   , eledger.fn_get_attribute_value(et.id, 'PERIOD_SEGMENT') transaction_segment
                              from eledger.el_taxpayer tp
                                       inner join eledger.el_transaction et
                                                  on et.taxpayer_id = tp.id
                                       inner join eledger.el_accounting ea
                                                  on ea.transaction_id = et.id
                                       inner join eledger_config.el_gl_account ega
                                                  on ega.id = ea.gl_account_id
                              where tp.tpn = p_tpn
                                AND ega.code LIKE coalesce(p_tax_type_code, ega.code) || '%'
                                and (
                                      accounting_action_type_id in (2, 3)
                                      or
                                      (accounting_action_type_id = 1 and account_type = 'A' and transfer_type = 'D')
                                      or
                                      (accounting_action_type_id = 4 and account_type = 'A' and transfer_type = 'C')
                                  )
                          ) t
                     where transaction_year = coalesce(p_year, transaction_year)
                       and transaction_segment = coalesce(p_segment, transaction_segment)
                 ) r
        )

        select ret.row_type::varchar
             , case
                   when ret.row_type = 'Header' then eledger.fn_get_accounting_period_description(transaction_year,
                                                                                                  transaction_segment,
                                                                                                  p_language_code)
                   else period_description end period_description
             , case
                   when ret.row_type = 'Body' then eledger.fn_get_accounting_descrption(id, p_language_code)
                   else '' end as              description
             , ret.debit
             , ret.credit
             , ret.balance
             , ret.drn
             --, id, parent_id, gl_account_id, transfer_type, account_type, accounting_action_type_id
        from (
                 select 'Header'      as row_type
                      , p.id
                      , bal.transaction_year
                      , bal.transaction_segment
                      , null::varchar    period_description
                      , null::numeric    debit
                      , null::numeric    credit
                      , bal.balance
                      , null::varchar as drn
                      , parent_id
                      , gl_account_id
                      , transfer_type
                      , account_type
                      , accounting_action_type_id
                 from acc_bal bal
                          inner join
                      (
                          select distinct 0                                                       id
                                        , eledger.fn_get_attribute_value(et.id, 'PERIOD_YEAR')    transaction_year
                                        , eledger.fn_get_attribute_value(et.id, 'PERIOD_SEGMENT') transaction_segment
                                        , et.transaction_type_id
                          from eledger.el_taxpayer tp
                                   inner join eledger.el_transaction et
                                              on et.taxpayer_id = tp.id
                          where tp.tpn = p_tpn
                            and et.transaction_type_id <> 3
                      ) p
                      on bal.transaction_year = p.transaction_year and bal.transaction_segment = p.transaction_segment
                 where bal.id = bal.m_id

                 union all

                 select 'Body' as                                      row_type
                      , id
                      , transaction_year
                      , transaction_segment
                      , period_description
                      , bal.debit
                      , bal.credit
                      , bal.balance
                      , case
                            when accounting_action_type_id = 4 then eledger.fn_get_accounting_drn(parent_id)
                            else eledger.fn_get_accounting_drn(id) end drn
                      , parent_id
                      , gl_account_id
                      , transfer_type
                      , account_type
                      , accounting_action_type_id
                 from acc_bal bal
             ) ret
        order by ret.transaction_year, ret.transaction_segment, ret.id;

END;
$function$
;

--------------------------------------------------------------------------------------------------