INSERT INTO eledger_config.el_interest_calculation( id
                                                  , transaction_type_id
                                                  , accounting_action_type_id
                                                  , start_date_condition
                                                  , days_count_condition
                                                  , percent_condition
    --, max_amount_condition
                                                  , in_amount_condition
                                                  , case_condition
                                                  , description
                                                  , start_of_validity)
VALUES ( nextval('eledger_config.el_interest_calculation_id_seq')
       , (SELECT id FROM eledger_config.el_transaction_type WHERE name = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_accounting_action_type WHERE name = 'FINE_AND_PENALTY')
       , '[transaction].[settlement_date] - 1'
       , '1'
       , '1'
           --, '[transaction].[amount]'
       , '[property].[NU_LATE_FILL_PNL]'
       , '[transaction].[deadline] < [transaction].[settlement_date] and [transaction].[settlement_date] = p_calculation_date'
       , 'LATE FILLING'
       , '20210101');