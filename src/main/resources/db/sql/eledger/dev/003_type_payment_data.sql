INSERT INTO eledger_config.el_transaction_type(id, name)
VALUES (nextval('eledger_config.el_transaction_type_id_seq'), 'PAYMENT');

INSERT INTO eledger_config.el_transaction_type_description(id, language_code, value, transaction_type_id)
VALUES (nextval('eledger_config.el_transaction_type_description_id_seq'), 'EN', 'Transaction type for payment',
        (SELECT id FROM eledger_config.el_transaction_type WHERE name = 'PAYMENT'));

INSERT INTO eledger_config.el_transaction_type_attribute(id, name, data_type_id)
VALUES (nextval('eledger_config.el_transaction_type_attribute_id_seq'), 'TARGET_TRANSACTION_ID', 7)
     , (nextval('eledger_config.el_transaction_type_attribute_id_seq'), 'TARGET_DRN', 6);

INSERT INTO eledger_config.el_transaction_type_attribute_description(id, language_code, value, transaction_type_attribute_id)
VALUES (nextval('eledger_config.el_transaction_type_attribute_description_id_seq'), 'EN', 'Payable liability drn',
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE name = 'TARGET_DRN'))
     , (nextval('eledger_config.el_transaction_type_attribute_description_id_seq'), 'EN',
        'Payable liability transaction',
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE name = 'TARGET_TRANSACTION_ID'));


INSERT INTO eledger_config.el_transaction_type_transaction_type_attribute(transaction_type_id, transaction_type_attribute_id)
VALUES ((SELECT id FROM eledger_config.el_transaction_type WHERE name = 'PAYMENT'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE name = 'TARGET_TRANSACTION_ID'))
     , ((SELECT id FROM eledger_config.el_transaction_type WHERE name = 'PAYMENT'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE name = 'TARGET_DRN'));