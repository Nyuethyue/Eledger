INSERT INTO eledger_config.el_property (id, code, data_type_id, value, start_of_validity)
VALUES (nextval('eledger_config.el_property_id_seq'), 'YEARLY_PERCENT', 3, '24', '20210101')
     , (nextval('eledger_config.el_property_id_seq'), 'NU_LATE_FILL_PNL', 3, '5000', '20210101');

INSERT INTO eledger_config.el_property_description(id, language_code, value, property_id)
VALUES (nextval('eledger_config.el_property_description_id_seq'), 'EN', 'Yearly interest percent', (SELECT id
                                                                                                    FROM eledger_config.el_property
                                                                                                    WHERE code = 'YEARLY_PERCENT'
                                                                                                      AND start_of_validity = '20210101'::date))
     , (nextval('eledger_config.el_property_description_id_seq'), 'EN', 'Calculation penalty for late fill', (SELECT id
                                                                                                              FROM eledger_config.el_property
                                                                                                              WHERE code = 'NU_LATE_FILL_PNL'
                                                                                                                AND start_of_validity = '20210101'::date));
