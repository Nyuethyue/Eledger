----------------------------------------------------------------------------------------------------
INSERT INTO ref.currency(id, code, symbol)
VALUES (NEXTVAL('ref.currency_id_seq'), 'BTN','Nu');

INSERT INTO ref.currency_description(id, language_code, value, currency_id)
VALUES (NEXTVAL('ref.currency_description_id_seq'), 'en', 'BTN', CURRVAL('ref.currency_id_seq'));


INSERT INTO ref.currency(id, code, symbol)
VALUES (NEXTVAL('ref.currency_id_seq'), 'USD','$');

INSERT INTO ref.currency_description(id, language_code, value, currency_id)
VALUES (NEXTVAL('ref.currency_description_id_seq'), 'en', 'USD', CURRVAL('ref.currency_id_seq'));


INSERT INTO ref.currency(id, code, symbol)
VALUES (NEXTVAL('ref.currency_id_seq'), 'INR','₹');

INSERT INTO ref.currency_description(id, language_code, value, currency_id)
VALUES (NEXTVAL('ref.currency_description_id_seq'), 'en', 'INR', CURRVAL('ref.currency_id_seq'));