INSERT INTO eledger_config.el_transaction_type(id, code)
VALUES (NEXTVAL('eledger_config.el_transaction_type_id_seq'), 'PAYMENT');

INSERT INTO eledger_config.el_transaction_type_description(id, language_code, value, transaction_type_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_description_id_seq'), 'EN', 'Transaction type for payment',
        (SELECT id FROM eledger_config.el_transaction_type WHERE code = 'PAYMENT'));

INSERT INTO eledger_config.el_transaction_type_attribute(id, code, data_type_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_attribute_id_seq'), 'TARGET_TRANSACTION_ID', 7)
     , (NEXTVAL('eledger_config.el_transaction_type_attribute_id_seq'), 'TARGET_DRN', 6);

INSERT INTO eledger_config.el_transaction_type_attribute_description(id, language_code, value, transaction_type_attribute_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_attribute_description_id_seq'), 'EN', 'Payable liability drn',
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'TARGET_DRN'))
     , (NEXTVAL('eledger_config.el_transaction_type_attribute_description_id_seq'), 'EN',
        'Payable liability transaction',
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'TARGET_TRANSACTION_ID'));


INSERT INTO eledger_config.el_transaction_type_transaction_type_attribute(transaction_type_id, transaction_type_attribute_id)
VALUES ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'PAYMENT'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'TARGET_TRANSACTION_ID'))
     , ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'PAYMENT'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'TARGET_DRN'));
