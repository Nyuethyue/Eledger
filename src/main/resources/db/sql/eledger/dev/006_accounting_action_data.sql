INSERT INTO eledger_config.el_accounting_action(id, name)
VALUES (nextval('eledger_config.el_accounting_action_id_seq'), 'CREATION')
     , (nextval('eledger_config.el_accounting_action_id_seq'), 'ACCUMULATION')
     , (nextval('eledger_config.el_accounting_action_id_seq'), 'TRANSFER');

INSERT INTO eledger_config.el_accounting_action_type (id, accounting_action_id, name)
VALUES ( nextval('eledger_config.el_accounting_action_type_id_seq')
       , (SELECT id FROM eledger_config.el_accounting_action WHERE name = 'CREATION')
       , 'FORMULAIT')
     , ( nextval('eledger_config.el_accounting_action_type_id_seq')
       , (SELECT id FROM eledger_config.el_accounting_action WHERE name = 'ACCUMULATION')
       , 'INTEREST')
     , ( nextval('eledger_config.el_accounting_action_type_id_seq')
       , (SELECT id FROM eledger_config.el_accounting_action WHERE name = 'ACCUMULATION')
       , 'FINE_AND_PENALTY')
     , ( nextval('eledger_config.el_accounting_action_type_id_seq')
       , (SELECT id FROM eledger_config.el_accounting_action WHERE name = 'TRANSFER')
       , 'PAYMENT');

INSERT INTO eledger_config.el_accounting_action_type(id, accounting_action_id, name)
VALUES (nextval('eledger_config.el_accounting_action_type_id_seq'),
        (SELECT id FROM eledger_config.el_accounting_action WHERE name = 'TRANSFER'), 'REPAY_NET_NEGATIVE');

INSERT INTO eledger_config.el_accounting_action_type(id, accounting_action_id, name)
VALUES (nextval('eledger_config.el_accounting_action_type_id_seq'),
        (SELECT id FROM eledger_config.el_accounting_action WHERE name = 'TRANSFER'),
        'NET_NEGATIVE_TO_TAXPAYER_ACCOUNT');
