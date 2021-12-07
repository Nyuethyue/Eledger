------------------------------------------------------------------------------------------------------------------------

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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11411010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11411020001')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11421010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11421020020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11411010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11411020001')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11421010020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
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
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11421020020')
       , (SELECT ettga.id
          FROM eledger_config.el_transaction_type_gl_account ettga
                   INNER JOIN eledger_config.el_transaction_type ett
                              ON ett.id = ettga.transaction_type_id
                   INNER JOIN eledger_config.el_gl_account ega
                              ON ega.id = ettga.gl_account_id
          WHERE ett.code = 'LIABILITY'
            AND ega.code = '11421990002'));

------------------------------------------------------------------------------------------------------------------------

INSERT INTO eledger_config.el_transaction_couples(id, accounting_action_type_id, from_transaction_type_account_id,
                                                  to_transaction_type_account_id)
SELECT nextval('eledger_config.el_transaction_couples_id_seq')                          id
     , (SELECT id FROM eledger_config.el_accounting_action_type WHERE name = 'PAYMENT') accounting_action_id
     , p.id
     , a.id
FROM eledger_config.el_transaction_type_gl_account p
         INNER JOIN eledger_config.el_transaction_type pett
                    ON pett.id = p.transaction_type_id
         INNER JOIN eledger_config.el_transaction_type_gl_account a
                    ON a.gl_account_id = p.gl_account_id
         INNER JOIN eledger_config.el_transaction_type aett
                    ON aett.id = a.transaction_type_id
WHERE p.account_type = 'P'
  AND a.account_type = 'A'
  AND pett.code = 'PAYMENT'
  AND aett.code = 'LIABILITY';

------------------------------------------------------------------------------------------------------------------------

INSERT INTO eledger_config.el_transaction_couples(id, accounting_action_type_id, from_transaction_type_account_id,
                                                  to_transaction_type_account_id)
SELECT nextval('eledger_config.el_transaction_couples_id_seq')                                     id
     , (SELECT id FROM eledger_config.el_accounting_action_type WHERE name = 'REPAY_NET_NEGATIVE') accounting_action_id
     , p.id
     , a.id
FROM eledger_config.el_transaction_type_gl_account p
         INNER JOIN eledger_config.el_transaction_type pett
                    ON pett.id = p.transaction_type_id
         INNER JOIN eledger_config.el_transaction_type_gl_account a
                    ON a.gl_account_id = p.gl_account_id
         INNER JOIN eledger_config.el_transaction_type aett
                    ON aett.id = a.transaction_type_id
WHERE p.account_type = 'P'
  AND a.account_type = 'A'
  AND pett.code = 'NET_NEGATIVE'
  AND aett.code = 'LIABILITY';

------------------------------------------------------------------------------------------------------------------------
