INSERT INTO eledger_config.el_transaction_type(id, code)
VALUES (NEXTVAL('eledger_config.el_transaction_type_id_seq'), 'REFUND_FROM_NET_NEGATIVE');

INSERT INTO eledger_config.el_transaction_type_description(id, language_code, value, transaction_type_id)
VALUES (NEXTVAL('eledger_config.el_transaction_type_description_id_seq'), 'EN',
        'Transaction type Refund from net_negative acccounts',
        (SELECT id FROM eledger_config.el_transaction_type WHERE code = 'REFUND_FROM_NET_NEGATIVE'));

INSERT INTO eledger_config.el_transaction_type_transaction_type_attribute(transaction_type_id, transaction_type_attribute_id)
VALUES ((SELECT id FROM eledger_config.el_transaction_type WHERE code = 'REFUND_FROM_NET_NEGATIVE'),
        (SELECT id FROM eledger_config.el_transaction_type_attribute WHERE code = 'TARGET_TRANSACTION_ID'));