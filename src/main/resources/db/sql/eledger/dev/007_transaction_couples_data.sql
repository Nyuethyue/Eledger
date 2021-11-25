INSERT INTO eledger_config.el_transaction_couples(id, accounting_action_type_id, from_transaction_type_account_id,
                                                  to_transaction_type_account_id)
VALUES ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'INTEREST')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411990001'))
     , ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'INTEREST')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411020001')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411990001'))
     , ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'INTEREST')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421990001'))
     , ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'INTEREST')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421020020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421990001'));


INSERT INTO eledger_config.el_transaction_couples(id, accounting_action_type_id, from_transaction_type_account_id,
                                                  to_transaction_type_account_id)
VALUES ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'FINE_AND_PENALTY')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411990002'))
     , ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'FINE_AND_PENALTY')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411020001')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11411990002'))
     , ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'FINE_AND_PENALTY')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421990002'))
     , ( nextval('eledger_config.el_transaction_couples_id_seq'), (SELECT id
                                                                   FROM eledger_config.el_accounting_action_type
                                                                   WHERE name = 'FINE_AND_PENALTY')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421020020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.name = 'LIABILITY'
            AND ega.code = '11421990002'));
