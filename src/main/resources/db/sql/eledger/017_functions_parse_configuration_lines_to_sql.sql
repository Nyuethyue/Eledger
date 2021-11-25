-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger_config.fn_data_type(p_data_type_id integer)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    SELECT type INTO v_ret_val FROM eledger_config.el_data_type WHERE id = p_data_type_id;

    RETURN upper(v_ret_val);
END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger_config.fn_count_key_word(p_text character varying, p_key_word character varying)
    RETURNS integer
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val int = 0;
BEGIN
    v_ret_val := ((length(p_text) - length(replace(p_text, p_key_word, ''))) / length(p_key_word))::int;

    RETURN v_ret_val;
END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger_config.fn_function_data_type_id(p_type character varying)
    RETURNS integer
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val int;
BEGIN

    IF upper(p_type) IN ('CURRENT_YEAR_DAYS_COUNT')
    THEN
        SELECT dt.id
        INTO v_ret_val
        FROM eledger_config.el_data_type dt
        WHERE dt.type = 'INT';
        RETURN v_ret_val;
    END IF;

    IF upper(p_type) IN ('REMAIN_AMOUNT')
    THEN
        SELECT dt.id
        INTO v_ret_val
        FROM eledger_config.el_data_type dt
        WHERE dt.type = 'DECIMAL';
        RETURN v_ret_val;
    END IF;

    RETURN coalesce(v_ret_val, 0);

END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger_config.fn_property_data_type_id(p_type character varying)
    RETURNS integer
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val int;
BEGIN

    SELECT p.data_type_id
    INTO v_ret_val
    FROM eledger_config.el_property p
    WHERE upper(p.code) = upper(p_type)
    LIMIT 1;

    RETURN coalesce(v_ret_val, 0);

END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger_config.fn_attribute_data_type_id(p_type character varying)
    RETURNS integer
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val int;
BEGIN

    IF upper(p_type) IN ('AMOUNT', 'REMAIN_AMOUNT')
    THEN
        SELECT dt.id
        INTO v_ret_val
        FROM eledger_config.el_data_type dt
        WHERE dt.type = 'DECIMAL';
        RETURN v_ret_val;
    END IF;

    IF upper(p_type) IN ('DRN', 'GL_ACCOUNT_CODE')
    THEN
        SELECT dt.id
        INTO v_ret_val
        FROM eledger_config.el_data_type dt
        WHERE dt.type = 'TEXT';
        RETURN v_ret_val;
    END IF;

    IF upper(p_type) IN ('SETTLEMENT_DATE')
    THEN
        SELECT dt.id
        INTO v_ret_val
        FROM eledger_config.el_data_type dt
        WHERE dt.type = 'DATE';
        RETURN v_ret_val;
    END IF;

    IF upper(p_type) IN ('CREATION_DATE_TIME')
    THEN
        SELECT dt.id
        INTO v_ret_val
        FROM eledger_config.el_data_type dt
        WHERE dt.type = 'TIMESTAMP';
        RETURN v_ret_val;
    END IF;

    SELECT a.data_type_id
    INTO v_ret_val
    FROM eledger_config.el_transaction_type_attribute a
    WHERE upper(a.name) = upper(p_type);

    RETURN coalesce(v_ret_val, 0);

END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_attribute_value(p_transaction_id bigint, p_attribute_type character varying)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val         varchar;
    v_main_gl_account varchar;
    v_is_attribute    int;
BEGIN

    SELECT count(*)
    INTO v_is_attribute
    FROM eledger_config.el_transaction_type_attribute tta
    WHERE upper(tta.name) = upper(p_attribute_type);

    IF v_is_attribute > 0
    THEN
        SELECT ta.value
        INTO v_ret_val
        FROM eledger.el_transaction t
                 INNER JOIN eledger.el_transaction_attribute ta
                            ON ta.transaction_id = t.id
                 INNER JOIN eledger_config.el_transaction_type_attribute tta
                            ON tta.id = ta.transaction_type_attribute_id
        WHERE t.id = p_transaction_id
          AND upper(tta.name) = upper(p_attribute_type)
        LIMIT 1;
        RETURN v_ret_val;
    END IF;

    --'DRN', 'GL_ACCOUNT_CODE', 'SETTLEMENT_DATE', 'AMOUNT', 'CREATION_DATE_TIME'

    IF upper(p_attribute_type) = 'DRN' THEN
        SELECT t.drn::varchar
        INTO v_ret_val
        FROM eledger.el_transaction t
        WHERE t.id = p_transaction_id;
        RETURN v_ret_val;
    END IF;

    IF upper(p_attribute_type) = 'GL_ACCOUNT_CODE' THEN
        SELECT t.gl_account_code::varchar
        INTO v_ret_val
        FROM eledger.el_transaction t
        WHERE t.id = p_transaction_id;
        RETURN v_ret_val;
    END IF;

    IF upper(p_attribute_type) = 'AMOUNT' THEN
        SELECT t.amount::varchar
        INTO v_ret_val
        FROM eledger.el_transaction t
        WHERE t.id = p_transaction_id;
        RETURN v_ret_val;
    END IF;

    IF upper(p_attribute_type) = 'SETTLEMENT_DATE' THEN
        SELECT t.settlement_date::varchar
        INTO v_ret_val
        FROM eledger.el_transaction t
        WHERE t.id = p_transaction_id;
        RETURN v_ret_val;
    END IF;

    IF upper(p_attribute_type) = 'CREATION_DATE_TIME' THEN
        SELECT t.creation_date_time::varchar
        INTO v_ret_val
        FROM eledger.el_transaction t
        WHERE t.id = p_transaction_id;
        RETURN v_ret_val;
    END IF;

    RETURN coalesce(v_ret_val, '');


END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_function_value(p_transaction_id bigint, p_type character varying,
                                                         p_calculation_date date)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val         varchar;
    v_main_gl_account varchar;
BEGIN

    IF upper(p_type) = 'CURRENT_YEAR_DAYS_COUNT' THEN

        SELECT ((t.yr || '1231')::date - (t.yr || '0101')::date + 1)::varchar
        INTO v_ret_val
        FROM (
                 SELECT extract(YEAR FROM current_date)::varchar yr
             ) t;

        RETURN v_ret_val;
    END IF;

    IF upper(p_type) = 'REMAIN_AMOUNT'
    THEN
        SELECT sum(a.amount * (CASE WHEN transfer_type = 'D' THEN 1 ELSE -1 END))::varchar
        INTO v_ret_val
        FROM eledger.el_accounting a
                 INNER JOIN eledger_config.el_gl_account ga
                            ON ga.id = a.gl_account_id
                 INNER JOIN eledger.el_transaction et
                            ON et.id = a.transaction_id AND et.gl_account_code = ga.code
        WHERE a.transaction_id = p_transaction_id
          AND a.transaction_date <= p_calculation_date;

        RETURN coalesce(v_ret_val, '');

    END IF;

    RETURN coalesce(v_ret_val, '');


END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger.fn_get_property_value(p_type character varying, p_calculation_date date)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_ret_val varchar;
BEGIN

    SELECT p.value
    INTO v_ret_val
    FROM eledger_config.el_property p
    WHERE upper(p.code) = upper(p_type)
      AND p_calculation_date >= p.start_of_validity
      AND p_calculation_date < coalesce(p.end_of_validity, '99990101');

    RETURN coalesce(v_ret_val, '');

END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger_config.fn_convert_text_to_sql(p_text character varying)
    RETURNS character varying
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_count_key_word                   int     = 0;
    v_ret_val                          varchar = '';
    v_key_word_start                   int     = 0;
    v_key_word_end                     int     = 0;
    v_length_text                      int     = 0;
    v_type                             varchar;
    v_key_word                         varchar;
    v_block_start                      int     = 0;
    v_block_end                        int     = 0;
    v_key_type                         int;
    v_function                         varchar;
    v_argument                         varchar;
    v_is_default_transaction_attribute boolean;
BEGIN

    p_text := UPPER(p_text);
    v_count_key_word := v_count_key_word + eledger_config.fn_count_key_word(p_text, '[TRANSACTION].');
    v_count_key_word := v_count_key_word + eledger_config.fn_count_key_word(p_text, '[PROPERTY].');
    v_count_key_word := v_count_key_word + eledger_config.fn_count_key_word(p_text, '[FROM_TRANSACTION].');
    v_count_key_word := v_count_key_word + eledger_config.fn_count_key_word(p_text, '[TO_TRANSACTION].');
    v_count_key_word := v_count_key_word + eledger_config.fn_count_key_word(p_text, '[FUNCTION].');
    --v_count_key_word := v_count_key_word + config.fn_count_key_word(p_text, '[TAXPAYER].');
    --raise notice 'v_count_key_word: %', v_count_key_word::text;

    IF v_count_key_word = 0 THEN
        v_ret_val = p_text;
        RETURN v_ret_val;
    END IF;

    WHILE v_count_key_word > 0
        LOOP
            v_is_default_transaction_attribute := FALSE;
            v_length_text := length(p_text);
            v_key_word_start := position('[' IN p_text);
            v_ret_val := v_ret_val || substring(p_text, 1, v_key_word_start - 1);

            p_text := substring(p_text, v_key_word_start, v_length_text);
            v_key_word_end := position(']' IN p_text);

            v_key_word := substring(p_text, 1, v_key_word_end);
            IF v_key_word = '[TRANSACTION]' THEN
                v_function := 'eledger.fn_get_attribute_value(p_transaction_id, ';
            ELSEIF v_key_word = '[FROM_TRANSACTION]' THEN
                v_function := 'eledger.fn_get_attribute_value(p_from_transaction_id, ';
            ELSEIF v_key_word = '[TO_TRANSACTION]' THEN
                v_function := 'eledger.fn_get_attribute_value(p_to_transaction_id, ';
            ELSEIF v_key_word = '[PROPERTY]' THEN
                v_function := 'eledger.fn_get_property_value(';
            ELSEIF v_key_word = '[FUNCTION]' THEN
                v_function := 'eledger.fn_get_function_value(p_transaction_id, ';
            END IF;

            p_text := substring(p_text, v_key_word_end + 2, v_length_text);

            v_block_start := position('[' IN p_text);
            v_block_end := position(']' IN p_text);

            v_argument := substring(p_text, v_block_start, v_block_end);
            v_argument := replace(replace(v_argument, ']', ''), '[', '');

            v_function := v_function || '''' || v_argument || '''';

            IF v_key_word IN ('[TRANSACTION]', '[FROM_TRANSACTION]', '[TO_TRANSACTION]') THEN
                v_key_type := eledger_config.fn_attribute_data_type_id(v_argument);
            ELSEIF v_key_word = '[FUNCTION]' THEN
                v_key_type := eledger_config.fn_function_data_type_id(v_argument);
                v_function := v_function || ', p_calculation_date';
            ELSEIF v_key_word = '[PROPERTY]' THEN
                v_key_type := eledger_config.fn_property_data_type_id(v_argument);
                v_function := v_function || ', p_calculation_date';
            END IF;

            v_function := 'cast(' || v_function || ') as ' || eledger_config.fn_data_type(v_key_type) || ')';

            v_ret_val := v_ret_val || v_function;
            p_text := substring(p_text, v_block_end + 1, v_length_text);

            v_count_key_word := v_count_key_word - 1;

        END LOOP;

    IF v_count_key_word = 0 THEN
        v_ret_val = v_ret_val || p_text;
    END IF;

    RETURN upper(v_ret_val);
END;
$function$
;

-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE eledger_config.sp_create_function(IN p_function_name character varying,
                                                              IN p_formula character varying,
                                                              IN p_return_type character varying)
    LANGUAGE plpgsql
AS
$procedure$
DECLARE
    v_create_statement   varchar;
    v_function_statement varchar;
    v_sql_forluma        varchar;
    v_exec_script        varchar;
    v_parameters_list    varchar;
BEGIN

    v_create_statement := 'create ';

    IF exists(SELECT 1
              FROM pg_proc p
                       INNER JOIN pg_namespace n ON n.oid = p.pronamespace
              WHERE n.nspname = 'eledger'
                AND p.proname = p_function_name)
    THEN
        v_create_statement := 'create or replace ';
    END IF;

    v_sql_forluma := eledger_config.fn_convert_text_to_sql(p_formula);

    v_parameters_list := '(p_transaction_id int , p_calculation_date date) ';

    v_function_statement := v_create_statement || 'function eledger.' || p_function_name || v_parameters_list ||
                            '	returns ' || p_return_type || '
										language plpgsql
										as $function$
										begin
											return ' || v_sql_forluma || ';
										end;
										$function$;';

    EXECUTE v_function_statement;

END;
$procedure$
;

-----------------------------------------------------------------------------------------------------------------------------
