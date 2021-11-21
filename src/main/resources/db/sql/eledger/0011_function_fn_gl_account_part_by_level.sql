CREATE OR REPLACE FUNCTION eledger_config.fn_gl_account_part_by_level(p_level integer, p_calculation_date timestamp without time zone, p_language_code character varying default null)
 RETURNS TABLE(id bigint, gl_account_part character varying, current_code character varying, language_code character varying, description character varying, level integer)
 LANGUAGE plpgsql
AS $function$
begin
	return query

		WITH RECURSIVE w_rec
		as (
		    SELECT e.id, parent_id as parent_id, 1 AS level, code::text as gl_acc
		    FROM eledger_config.el_gl_account_part e
		    WHERE parent_id is null
		    	and p_calculation_date between start_of_validity and  coalesce(end_of_validity, '99990101'::timestamp)
        UNION
		    SELECT p.id, p.parent_id , d.level + 1 as level, d.gl_acc::text || p.code as gl_acc
		    FROM eledger_config.el_gl_account_part p
		    INNER JOIN w_rec d
		    ON p.parent_id = d.id
		    where p_calculation_date between p.start_of_validity and  coalesce(p.end_of_validity, '99990101'::timestamp)
		)

		SELECT t.id, r.gl_acc::varchar as gl_acc, t.code::varchar as current_code
			, d.language_code, d.value, r.level
		FROM w_rec r
		INNER JOIN eledger_config.el_gl_account_part t
		ON r.id = t.id
		inner join eledger_config.el_gl_account_part_description d
		on d.gl_account_part_id = t.id
		WHERE r.level = p_level
			and coalesce(p_language_code, d.language_code) = d.language_code;

end;
$function$
;