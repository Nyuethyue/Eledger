--------------------------------------------------------------------------------------------------------
INSERT INTO ref.bank_branch(id, code, branch_code, address, start_of_validity, end_of_validity, bank_id)
VALUES (NEXTVAL('ref.bank_branch_id_seq'), 'BHUB9100201', '20', 'Chubachu Thimphu', '20210101', null,
        (SELECT id FROM ref.bank WHERE code = 'BHUB9100200'));

INSERT INTO ref.bank_branch_description(id, language_code, value, bank_branch_id)
VALUES (NEXTVAL('ref.bank_branch_description_id_seq'), 'en', 'Bank of Bhutan (Thimphu) Branch',
        CURRVAL('ref.bank_branch_id_seq'));

INSERT INTO ref.bank_branch(id, code, branch_code, address, start_of_validity, end_of_validity, bank_id)
VALUES (NEXTVAL('ref.bank_branch_id_seq'), 'BNBT9100301', null, 'Chubachu Thimphu', '20210101', null,
        (SELECT id FROM ref.bank WHERE code = 'BNBT9100300'));

INSERT INTO ref.bank_branch_description(id, language_code, value, bank_branch_id)
VALUES (NEXTVAL('ref.bank_branch_description_id_seq'), 'en', 'Bhutan National Bank (Thimphu) Main Branch',
        CURRVAL('ref.bank_branch_id_seq'));





