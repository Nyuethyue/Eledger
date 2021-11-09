-----------------------------------------------------------------------------------------------------------------------------

INSERT INTO eledger_config.gl_account_part_type(id, level, creation_date_time, last_modification_date_time)
VALUES (NEXTVAL('eledger_config.gl_account_part_type_id_seq'), 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (NEXTVAL('eledger_config.gl_account_part_type_id_seq'), 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (NEXTVAL('eledger_config.gl_account_part_type_id_seq'), 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (NEXTVAL('eledger_config.gl_account_part_type_id_seq'), 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (NEXTVAL('eledger_config.gl_account_part_type_id_seq'), 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (NEXTVAL('eledger_config.gl_account_part_type_id_seq'), 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
     , (NEXTVAL('eledger_config.gl_account_part_type_id_seq'), 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


INSERT INTO eledger_config.gl_account_part_type_description(id, language_code, value, gl_account_part_type_id)
SELECT NEXTVAL('eledger_config.gl_account_part_type_description_id_seq') AS id
     , 'en'                                                      AS lang
     , CASE level
           WHEN 1 THEN 'Major group'
           WHEN 2 THEN 'Revenue group'
           WHEN 3 THEN 'Revenue type'
           WHEN 4 THEN 'Revenue Sub-type'
           WHEN 5 THEN 'Functional group'
           WHEN 6 THEN 'Functional type'
           WHEN 7 THEN 'Revenue Head/GL Head' END                AS level_description
     , id                                                        AS gl_account_part_type_id
FROM eledger_config.gl_account_part_type;

-----------------------------------------------------------------------------------------------------------------------------
