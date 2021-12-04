INSERT INTO eledger_config.el_transaction_type(id, code)
VALUES (NEXTVAL('eledger_config.el_transaction_type_id_seq'), 'NET_NEGATIVE');

INSERT INTO eledger_config.el_transaction_type_description(id, language_code, value, transaction_type_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_description_id_seq'), 'EN',
        'Transaction type for liability overpayment',
        (SELECT id FROM eledger_config.el_transaction_type WHERE code = 'NET_NEGATIVE'));

INSERT INTO eledger_config.el_transaction_type_transaction_type_attribute(transaction_type_id, transaction_type_attribute_id)
VALUES ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'NET_NEGATIVE'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'PERIOD_YEAR'))
     , ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'NET_NEGATIVE'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'PERIOD_SEGMENT'));
