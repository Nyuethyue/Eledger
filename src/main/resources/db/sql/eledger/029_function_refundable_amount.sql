
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

CREATE OR REPLACE FUNCTION eledger.fn_get_refundable_amount(p_tpn varchar, p_tax_type_code varchar, p_calculation_date date)
 RETURNS TABLE(transaction_id bigint, gl_account_id bigint, net_negative_type varchar, drn varchar, period_year varchar, period_segment varchar, balance numeric)
 LANGUAGE plpgsql
AS $function$
BEGIN
    RETURN QUERY

        select tb.transaction_id
        	, tb.gl_account_id
        	, ett.code as net_negative_type
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
		where tp.tpn = p_tpn and ett.code = 'NET_NEGATIVE_66'
			and ega.code LIKE COALESCE(p_tax_type_code, ega.code) || '%'
        order BY tb.transaction_id, tb.transaction_type_id, tb.gl_account_id;

END;
$function$
;

----------------------------------------------------------------------------------------------------------------------------------------------------

