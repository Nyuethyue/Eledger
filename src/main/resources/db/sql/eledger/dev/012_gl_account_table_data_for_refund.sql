
INSERT INTO eledger_config.el_transaction_type_gl_account(id, transaction_type_id, gl_account_id, account_type)
VALUES ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'REFUND_FROM_NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411010020'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'REFUND_FROM_NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411020001'), 'A')


     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'REFUND_FROM_NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421010020'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'REFUND_FROM_NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421020020'), 'A');