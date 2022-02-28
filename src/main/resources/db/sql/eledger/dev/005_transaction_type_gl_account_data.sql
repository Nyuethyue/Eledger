INSERT INTO eledger_config.el_transaction_type_gl_account(id, transaction_type_id, gl_account_id, account_type)
VALUES ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411010020'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411020001'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411990001'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411990002'), 'A')


     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421010020'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421020020'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421990001'), 'A')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421990002'), 'A');


INSERT INTO eledger_config.el_transaction_type_gl_account(id, transaction_type_id, gl_account_id, account_type)
VALUES ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411010020'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411020001'), 'P')


     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421010020'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421020020'), 'P');


INSERT INTO eledger_config.el_transaction_type_gl_account(id, transaction_type_id, gl_account_id, account_type)
VALUES ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411010020'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411020001'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411990001'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411990002'), 'P')


     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421010020'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421020020'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421990001'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'PAYMENT')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421990002'), 'P');

INSERT INTO eledger_config.el_transaction_type_gl_account(id, transaction_type_id, gl_account_id, account_type)
VALUES ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE_66')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411010020'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE_66')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11411020001'), 'P')


     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE_66')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421010020'), 'P')
     , ( nextval('eledger_config.el_transaction_type_gl_account_id_seq'), (SELECT id
                                                                           FROM eledger_config.el_transaction_type
                                                                           WHERE code = 'NET_NEGATIVE_66')
       , (SELECT id FROM eledger_config.el_gl_account WHERE code = '11421020020'), 'P');