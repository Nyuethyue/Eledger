----------------------------------------------------------------------------------------------------
INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'CASH');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'Cash', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'CHEQUE');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'Cheque', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'DEMAND_DRAFT');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'Demand Draft', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'CASH_WARRANT');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'Cash Warrant', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'ADVANCE_DEPOSIT');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'Advance Deposit', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'POS');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'POS', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'RMA_PAYMENT');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'RMA_Payment', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'M_BOB');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'mBOB', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'BOB_INTERNET_BANKING');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'BOB Internet Banking', CURRVAL('ref.payment_mode_id_seq'));

----------------------------------------------------------------------------------------------------

INSERT INTO ref.payment_mode(id, code)
VALUES (NEXTVAL('ref.payment_mode_id_seq'), 'BOB_COUNTER');

INSERT INTO ref.payment_mode_description(id, language_code, value, payment_mode_id)
VALUES (NEXTVAL('ref.payment_mode_description_id_seq'), 'en', 'BOB Counter', CURRVAL('ref.payment_mode_id_seq'));
