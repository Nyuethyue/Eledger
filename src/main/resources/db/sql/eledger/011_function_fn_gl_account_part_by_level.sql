CREATE OR REPLACE FUNCTION eledger_config.fn_gl_account_part_by_level(p_level integer,
                                                                      p_calculation_date timestamp WITHOUT TIME ZONE,
                                                                      p_language_code character varying DEFAULT NULL)
    RETURNS TABLE
            (
                id              bigint,
                gl_account_part character varying,
                current_code    character varying,
                language_code   character varying,
                description     character varying,
                level           integer
            )
    LANGUAGE plpgsql
AS
$function$
BEGIN
    RETURN QUERY

    WITH RECURSIVE w_rec
                       AS (
            SELECT e.id, parent_id AS parent_id, 1 AS level, code AS gl_acc
            FROM eledger_config.el_gl_account_part e
            WHERE parent_id IS NULL
              AND p_calculation_date BETWEEN start_of_validity AND COALESCE(end_of_validity, '99990101'::timestamp)
            UNION
            SELECT p.id, p.parent_id, d.level + 1 AS level, d.gl_acc || p.code AS gl_acc
            FROM eledger_config.el_gl_account_part p
                     INNER JOIN w_rec d
                                ON p.parent_id = d.id
            WHERE p_calculation_date BETWEEN p.start_of_validity AND COALESCE(p.end_of_validity, '99990101'::timestamp)
        )

    SELECT t.id
         , r.gl_acc AS gl_acc
         , t.code   AS current_code
         , d.language_code
         , d.value
         , r.level
    FROM w_rec r
             INNER JOIN eledger_config.el_gl_account_part t
                        ON r.id = t.id
             INNER JOIN eledger_config.el_gl_account_part_description d
                        ON d.gl_account_part_id = t.id
    WHERE r.level = p_level
      AND COALESCE(p_language_code, d.language_code) = d.language_code;

END;
$function$
;