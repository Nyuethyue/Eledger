
-- to  do
INSERT INTO eledger_config.el_transaction_couples(id, accounting_action_type_id, from_transaction_type_account_id,
                                                  to_transaction_type_account_id)
select nextval('eledger_config.el_transaction_couples_id_seq') id
     , (SELECT id
        FROM eledger_config.el_accounting_action_type
        WHERE name = 'NET_NEGATIVE_TO_TAXPAYER_ACCOUNT')       accounting_action_id
     , f.id
     , t.id
from (
         select ettga.*, egad.value
         from eledger_config.el_transaction_type_gl_account ettga
                  inner join eledger_config.el_transaction_type ett
                             on ettga.transaction_type_id = ett.id
                  inner join eledger_config.el_gl_account_description egad
                             on egad.gl_account_id = ettga.gl_account_id
         where ett.code in ('NET_NEGATIVE', 'NET_NEGATIVE_66')
           and egad.language_code = 'en'
           and egad.value like '%(POS)'
     ) f
         cross join (
    select ettga.*, egad.value
    from eledger_config.el_transaction_type_gl_account ettga
             inner join eledger_config.el_transaction_type ett
                        on ettga.transaction_type_id = ett.id
             inner join eledger_config.el_gl_account_description egad
                        on egad.gl_account_id = ettga.gl_account_id
    where ett.code = 'REFUND_FROM_NET_NEGATIVE'
      and egad.language_code = 'en'
      and egad.value not like '%(POE)'
) t;