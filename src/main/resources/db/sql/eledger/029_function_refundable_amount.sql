
----------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_net_negative_balance(p_tpn varchar, p_calculation_date date)
 RETURNS numeric
 LANGUAGE plpgsql
AS $function$
DECLARE
    v_ret_val numeric;
BEGIN

	select sum(-tb.balance) bal
   	into v_ret_val
	from eledger.el_taxpayer tp
	cross join eledger.fn_transaction_balance(tp.id, p_calculation_date) tb
	inner join eledger_config.el_transaction_type ett
	on ett.id = tb.transaction_type_id
	where tp.tpn = p_tpn and ett.code = 'NET_NEGATIVE' ;

    RETURN COALESCE(v_ret_val, 0);

END;
$function$
;

----------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_refundable_data_by_tax_type_codes(p_tpn character varying, p_tax_type_codes_list varchar[], p_calculation_date date)
 RETURNS TABLE(net_negative_type character varying, transaction_id bigint, tax_type_code varchar, gl_account_code varchar, gl_account_id bigint, drn character varying, period_year character varying, period_segment character varying, balance numeric)
 LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY

        select ett.code as net_negative_type
        	, tb.transaction_id
        	, egap.full_code tax_type_code
        	, ega.code gl_account_code
        	, tb.gl_account_id
			, eledger.fn_get_attribute_value(tb.transaction_id, 'drn') as drn
			, eledger.fn_get_attribute_value(tb.transaction_id, 'period_year') as period_year
			, eledger.fn_get_attribute_value(tb.transaction_id, 'period_segment') as period_segment
			, -tb.balance as balance
		from eledger.el_taxpayer tp
		cross join eledger.fn_transaction_balance(tp.id, p_calculation_date) tb
		inner join eledger_config.el_transaction_type ett
		on ett.id = tb.transaction_type_id
		inner join eledger_config.el_gl_account ega
		on ega.id = tb.gl_account_id
		inner join eledger_config.el_gl_account_part egap
		on ega.code like egap.full_code || '%' and egap.gl_account_part_type_id = 5
		where tp.tpn = p_tpn and ett.code = 'NET_NEGATIVE'
			 and egap.full_code = any(p_tax_type_codes_list)
        order BY 1, 2, 3, 4, 5;

END;
$function$
;

----------------------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_refundable_data_by_transaction_ids(p_tpn character varying, p_transaction_ids_list bigint[], p_calculation_date date)
 RETURNS TABLE(net_negative_type character varying, transaction_id bigint, tax_type_code varchar, gl_account_code varchar, gl_account_id bigint, drn character varying, period_year character varying, period_segment character varying, balance numeric)
 LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY

        select ett.code as net_negative_type
        	, tb.transaction_id
        	, egap.full_code tax_type_code
        	, ega.code gl_account_code
        	, tb.gl_account_id
			, eledger.fn_get_attribute_value(tb.transaction_id, 'drn') as drn
			, eledger.fn_get_attribute_value(tb.transaction_id, 'period_year') as period_year
			, eledger.fn_get_attribute_value(tb.transaction_id, 'period_segment') as period_segment
			, -tb.balance as balance
		from eledger.el_taxpayer tp
		cross join eledger.fn_transaction_balance(tp.id, p_calculation_date) tb
		inner join eledger_config.el_transaction_type ett
		on ett.id = tb.transaction_type_id
		inner join eledger_config.el_gl_account ega
		on ega.id = tb.gl_account_id
		inner join eledger_config.el_gl_account_part egap
		on ega.code like egap.full_code || '%' and egap.gl_account_part_type_id = 5
		where tp.tpn = p_tpn and ett.code = 'NET_NEGATIVE'
			 and tb.transaction_id = any(p_transaction_ids_list)
        order BY 1, 2, 3, 4, 5;

END;
$function$
;

----------------------------------------------------------------------------------------------------------------------------------------------------

