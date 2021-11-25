INSERT INTO eledger_config.el_transaction_type(id, name)
VALUES (nextval('eledger_config.el_transaction_type_id_seq'), 'OVERPAYMENT');

INSERT INTO eledger_config.el_transaction_type_description(id, language_code, value, transaction_type_id)
VALUES (nextval('eledger_config.el_transaction_type_description_id_seq'), 'EN',
        'Transaction type for liability overpayment',
        (SELECT id FROM eledger_config.el_transaction_type WHERE name = 'OVERPAYMENT'));

INSERT INTO eledger_config.el_transaction_type_transaction_type_attribute(transaction_type_id, transaction_type_attribute_id)
VALUES ((SELECT id FROM eledger_config.el_transaction_type WHERE name = 'OVERPAYMENT'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE name = 'PERIOD_YEAR'))
     , ((SELECT id FROM eledger_config.el_transaction_type WHERE name = 'OVERPAYMENT'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE name = 'PERIOD_SEGMENT'));