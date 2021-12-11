INSERT INTO ref.bank_account_gl_account_part(id, code)
VALUES (NEXTVAL('ref.bank_account_gl_account_part_id_seq'), 1);

INSERT INTO ref.bank_account(id, branch_id, code, start_of_validity, end_of_validity, is_primary_gl_account,
                             bank_account_gl_account_part_id)
VALUES (NEXTVAL('ref.bank_account_id_seq'), (SELECT id FROM ref.bank_branch WHERE code = 'BHUB9100201'), '19094033',
        '20210101', null, true, CURRVAL('ref.bank_account_gl_account_part_id_seq'));

INSERT INTO ref.bank_account_description(id, language_code, value, bank_account_id)
VALUES (NEXTVAL('ref.bank_account_description_id_seq'), 'en', 'Bob Revenue Account',
        CURRVAL('ref.bank_account_id_seq'));

INSERT INTO ref.bank_account_gl_account_part(id, code)
VALUES (NEXTVAL('ref.bank_account_gl_account_part_id_seq'), 2);

INSERT INTO ref.bank_account(id, branch_id, code, start_of_validity, end_of_validity, is_primary_gl_account,
                             bank_account_gl_account_part_id)
VALUES (NEXTVAL('ref.bank_account_id_seq'), (SELECT id FROM ref.bank_branch WHERE code = 'BHUB9100201'), '29094033',
        '20210101', null, true, CURRVAL('ref.bank_account_gl_account_part_id_seq'));

INSERT INTO ref.bank_account_description(id, language_code, value, bank_account_id)
VALUES (NEXTVAL('ref.bank_account_description_id_seq'), 'en', 'Bob Non-Revenue Account',
        CURRVAL('ref.bank_account_id_seq'));