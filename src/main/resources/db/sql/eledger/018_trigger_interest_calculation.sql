-----------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION eledger_config.fn_on_el_interest_calculation_change()
    RETURNS trigger
    LANGUAGE plpgsql
AS
$function$
DECLARE
    v_start_date_function  varchar;
    v_start_date_condition varchar;
    v_days_count_function  varchar;
    v_days_count_condition varchar;
    v_percent_function     varchar;
    v_percent_condition    varchar;
    v_in_amount_function   varchar;
    v_in_amount_condition  varchar;
    v_max_amount_function  varchar;
    v_max_amount_condition varchar;
    v_case_function        varchar;
    v_case_condition       varchar;

BEGIN


    IF TG_OP != 'DELETE'
    THEN
        v_start_date_function := 'fn_interest_start_' || new.id::text;
        v_days_count_function := 'fn_interest_days_' || new.id::text;
        v_percent_function := 'fn_interest_percent_' || new.id::text;
        v_in_amount_function := 'fn_interest_calc_amount_' || new.id::text;

        v_max_amount_function := 'fn_interest_max_amount_' || new.id::text;
        v_case_function := 'fn_interest_case_' || new.id::text;
    ELSE
        v_start_date_function := 'fn_interest_start_' || old.id::text;
        v_days_count_function := 'fn_interest_days_' || old.id::text;
        v_percent_function := 'fn_interest_percent_' || old.id::text;
        v_in_amount_function := 'fn_interest_calc_amount_' || old.id::text;

        v_max_amount_function := 'fn_interest_max_amount_' || old.id::text;
        v_case_function := 'fn_interest_case_' || old.id::text;
    END IF;


    IF EXISTS(SELECT NULL FROM pg_proc WHERE proname = v_start_date_function)
    THEN
        EXECUTE ('DROP FUNCTION IF EXISTS eledger.' || v_start_date_function);
        EXECUTE ('DROP FUNCTION IF EXISTS eledger.' || v_days_count_function);
        EXECUTE ('DROP FUNCTION IF EXISTS eledger.' || v_percent_function);
        EXECUTE ('DROP FUNCTION IF EXISTS eledger.' || v_in_amount_function);

        EXECUTE ('DROP FUNCTION IF EXISTS eledger.' || v_max_amount_function);
        EXECUTE ('DROP FUNCTION IF EXISTS eledger.' || v_case_function);
    END IF;

    IF TG_OP != 'DELETE'
    THEN

        --raise notice '% -> %', new.max_amount_condition, new.id;
        IF new.max_amount_condition IS NOT NULL
        THEN
            CALL eledger_config.sp_create_function(v_max_amount_function, new.max_amount_condition, 'decimal');
            new.max_amount_function := v_max_amount_function;
        END IF;

        --raise notice '% -> %', new.case_condition, new.id;
        IF new.case_condition IS NOT NULL
        THEN
            CALL eledger_config.sp_create_function(v_case_function, new.case_condition, 'boolean');
            new.case_function := v_case_function;
        END IF;
        -----------------------
        --raise notice '% -> %', new.start_date_condition, new.id;
        IF new.start_date_condition IS NOT NULL
        THEN
            CALL eledger_config.sp_create_function(v_start_date_function, new.start_date_condition, 'date');
            new.start_date_function := v_start_date_function;
        END IF;

        --raise notice '% -> %', new.days_count_condition, new.id;
        IF new.days_count_condition IS NOT NULL
        THEN
            CALL eledger_config.sp_create_function(v_days_count_function, new.days_count_condition, 'int');
            new.days_count_function := v_days_count_function;
        END IF;

        --raise notice '% -> %', new.percent_condition, new.id;
        IF new.percent_condition IS NOT NULL
        THEN
            CALL eledger_config.sp_create_function(v_percent_function, new.percent_condition, 'decimal');
            new.percent_function := v_percent_function;
        END IF;

        --raise notice '% -> %', new.in_amount_condition, new.id;
        IF new.in_amount_condition IS NOT NULL
        THEN
            CALL eledger_config.sp_create_function(v_in_amount_function, new.in_amount_condition, 'decimal');
            new.in_amount_function := v_in_amount_function;
        END IF;

        RETURN NEW;

    ELSE
        RETURN old;
    END IF;


END;
$function$
;

CREATE TRIGGER tg_el_interest_calculation_change
    BEFORE
        INSERT
        OR
        DELETE
        OR
        UPDATE
    ON
        eledger_config.el_interest_calculation
    FOR EACH ROW
EXECUTE FUNCTION eledger_config.fn_on_el_interest_calculation_change();

-----------------------------------------------------------------------------------------------------------------------------