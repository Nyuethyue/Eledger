----------------------------------------------------------------------------------------------------
INSERT INTO payment_mode(id, code)
VALUES (NEXTVAL('payment_mode_id_seq'), 'CASH');

INSERT INTO payment_mode_description(id, language_code,value,currency_id)
VALUES (NEXTVAL('payment_mode_description_id_seq'), 'en', 'Cash', CURRVAL('payment_mode_id_seq'));

INSERT INTO payment_mode(id, code)
VALUES (NEXTVAL('payment_mode_id_seq'), 'CHEQUE');

INSERT INTO payment_mode_description(id, language_code,value,currency_id)
VALUES (NEXTVAL('payment_mode_description_id_seq'), 'en', 'Cheque', CURRVAL('payment_mode_id_seq'));

INSERT INTO payment_mode(id, code)
VALUES (NEXTVAL('payment_mode_id_seq'), 'DEMAND_DRAFT');

INSERT INTO payment_mode_description(id, language_code,value,currency_id)
VALUES (NEXTVAL('payment_mode_description_id_seq'), 'en', 'Demand Draft', CURRVAL('payment_mode_id_seq'));

INSERT INTO payment_mode(id, code)
VALUES (NEXTVAL('payment_mode_id_seq'), 'CASH_WARRANT');

INSERT INTO payment_mode_description(id, language_code,value,currency_id)
VALUES (NEXTVAL('payment_mode_description_id_seq'), 'en', 'Cash Warrant', CURRVAL('payment_mode_id_seq'));

INSERT INTO payment_mode(id, code)
VALUES (NEXTVAL('payment_mode_id_seq'), 'ADVANCE_DEPOSIT');

INSERT INTO payment_mode_description(id, language_code,value,currency_id)
VALUES (NEXTVAL('payment_mode_description_id_seq'), 'en', 'Advance Deposit', CURRVAL('payment_mode_id_seq'));

INSERT INTO payment_mode(id, code)
VALUES (NEXTVAL('payment_mode_id_seq'), 'POS');

INSERT INTO payment_mode_description(id, language_code,value,currency_id)
VALUES (NEXTVAL('payment_mode_description_id_seq'), 'en', '{POS}', CURRVAL('payment_mode_id_seq'));

