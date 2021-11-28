INSERT INTO eledger_config.el_interest_calculation( id
                                                  , transaction_type_id
                                                  , accounting_action_type_id
                                                  , start_date_condition
    --, days_count_condition
                                                  , percent_condition
    --, max_amount_condition
                                                  , in_amount_condition
                                                  , case_condition
                                                  , description
                                                  , start_of_validity)
VALUES ( NEXTVAL('eledger_config.el_interest_calculation_id_seq')
       , (SELECT id FROM eledger_config.el_transaction_type WHERE code = 'LIABILITY')
       , (SELECT id FROM eledger_config.el_accounting_action_type WHERE name = 'INTEREST')
       , '[transaction].[deadline]'
           --, '10000'
       , '[property].[YEARLY_PERCENT] / 100 / [function].[current_year_days_count]'
           --, '[transaction].[amount]'
       , '[function].[remain_amount]'
       , '[transaction].[deadline] < p_calculation_date'
       , 'LATE PAYMENT'
       , '20210101');
