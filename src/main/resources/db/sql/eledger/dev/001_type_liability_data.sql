INSERT INTO eledger_config.el_transaction_type(id, code)
VALUES (NEXTVAL('eledger_config.el_transaction_type_id_seq'), 'LIABILITY');

INSERT INTO eledger_config.el_transaction_type_description(id, language_code, value, transaction_type_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_description_id_seq'), 'EN', 'Transaction type for liability',
        (SELECT id FROM eledger_config.el_transaction_type WHERE code = 'LIABILITY'));

INSERT INTO eledger_config.el_transaction_type_attribute(id, code, data_type_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_attribute_id_seq'), 'PERIOD_YEAR', 6)
     , (NEXTVAL('eledger_config.el_transaction_type_attribute_id_seq'), 'PERIOD_SEGMENT', 6)
     , (NEXTVAL('eledger_config.el_transaction_type_attribute_id_seq'), 'DEADLINE', 4);

INSERT INTO eledger_config.el_transaction_type_attribute_description(id, language_code, value, transaction_type_attribute_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_attribute_description_id_seq'), 'EN', 'Transaction year',
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'PERIOD_YEAR'))
     , (NEXTVAL('eledger_config.el_transaction_type_attribute_description_id_seq'), 'EN', 'Transaction period',
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'PERIOD_SEGMENT'))
     , (NEXTVAL('eledger_config.el_transaction_type_attribute_description_id_seq'), 'EN', 'Transaction dedline',
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'DEADLINE'));


INSERT INTO eledger_config.el_transaction_type_transaction_type_attribute(transaction_type_id, transaction_type_attribute_id)
VALUES ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'LIABILITY'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'PERIOD_YEAR'))
     , ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'LIABILITY'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'PERIOD_SEGMENT'))
     , ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'LIABILITY'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'DEADLINE'));
