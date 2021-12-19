---------------------------------------------------------------------------------------------
INSERT INTO ref.bank(id, code, start_of_validity, end_of_validity)
VALUES (NEXTVAL('ref.bank_id_seq'), 'BHUB9100200', '20210101', null);

INSERT INTO ref.bank_description(id, language_code, value, bank_id)
VALUES (NEXTVAL('ref.bank_description_id_seq'), 'en', 'Bank Of Bhutan', CURRVAL('ref.bank_id_seq'));

INSERT INTO ref.bank(id, code, start_of_validity, end_of_validity)
VALUES (NEXTVAL('ref.bank_id_seq'), 'BNBT9100300', '20210101', null);

INSERT INTO ref.bank_description(id, language_code, value, bank_id)
VALUES (NEXTVAL('ref.bank_description_id_seq'), 'en', 'Bhutan National Bank', CURRVAL('ref.bank_id_seq'));

