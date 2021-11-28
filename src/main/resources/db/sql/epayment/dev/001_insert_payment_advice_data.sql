INSERT INTO epayment.ep_taxpayer ("id", "tpn", "name", "creation_date_time")
VALUES (NEXTVAL('epayment.ep_taxpayer_id_seq'), 'TAB12345', 'Taxpayer name', NOW());


INSERT INTO epayment.ep_gl_account(id, code, creation_date_time)
VALUES (NEXTVAL('epayment.ep_gl_account_id_seq'), 'ACC1', NOW());

INSERT INTO epayment.ep_gl_account_description(id, language_code, "value", gl_account_id)
VALUES (NEXTVAL('epayment.ep_gl_account_description_id_seq'), 'en', 'Regular account 1',
        CURRVAL('epayment.ep_gl_account_id_seq'));

INSERT INTO epayment.ep_pa_bank_info(id, bank_account_number)
VALUES (NEXTVAL('epayment.ep_pa_bank_info_id_seq'), CURRVAL('epayment.ep_gl_account_id_seq'));

INSERT INTO epayment.ep_pa_bank_info_description(id, language_code, "value", pa_bank_info_id)
VALUES (NEXTVAL('epayment.ep_pa_bank_info_description_id_seq'), 'en', 'Bank 1',
        CURRVAL('epayment.ep_pa_bank_info_id_seq'));

INSERT INTO epayment.ep_payment_advice(id, drn, status, due_date, period_year, period_segment, creation_date_time, pan,
                                       taxpayer_id, pa_bank_info_id)
VALUES (NEXTVAL('epayment.ep_payment_advice_number_seq'), 'DRN1', 'INITIAL', CURRENT_DATE, CURRENT_DATE, CURRENT_DATE,
        CURRENT_DATE, 'PAN1', CURRVAL('epayment.ep_taxpayer_id_seq'), CURRVAL('epayment.ep_pa_bank_info_id_seq'));

INSERT INTO epayment.ep_pa_payable_line(id, amount, paid_amount, gl_account_id, payment_advice_id, el_transaction_id)
VALUES (NEXTVAL('epayment.ep_pa_payable_line_id_seq'), 300, 0, CURRVAL('epayment.ep_gl_account_id_seq'),
        CURRVAL('epayment.ep_payment_advice_number_seq'), 1);

---

INSERT INTO epayment.ep_gl_account(id, code, creation_date_time)
VALUES (NEXTVAL('epayment.ep_gl_account_id_seq'), 'ACC2', NOW());

INSERT INTO epayment.ep_gl_account_description(id, language_code, "value", gl_account_id)
VALUES (NEXTVAL('epayment.ep_gl_account_description_id_seq'), 'en', 'Regular account 2',
        CURRVAL('epayment.ep_gl_account_id_seq'));

INSERT INTO epayment.ep_pa_bank_info(id, bank_account_number)
VALUES (NEXTVAL('epayment.ep_pa_bank_info_id_seq'), CURRVAL('epayment.ep_gl_account_id_seq'));

INSERT INTO epayment.ep_pa_bank_info_description(id, language_code, "value", pa_bank_info_id)
VALUES (NEXTVAL('epayment.ep_pa_bank_info_description_id_seq'), 'en', 'Bank 2',
        CURRVAL('epayment.ep_pa_bank_info_id_seq'));

INSERT INTO epayment.ep_payment_advice(id, drn, status, due_date, period_year, period_segment, creation_date_time, pan,
                                       taxpayer_id, pa_bank_info_id)
VALUES (NEXTVAL('epayment.ep_payment_advice_number_seq'), 'DRN2', 'INITIAL', CURRENT_DATE, CURRENT_DATE, CURRENT_DATE,
        CURRENT_DATE, 'PAN2', CURRVAL('epayment.ep_taxpayer_id_seq'), CURRVAL('epayment.ep_pa_bank_info_id_seq'));

INSERT INTO epayment.ep_pa_payable_line(id, amount, paid_amount, gl_account_id, payment_advice_id, el_transaction_id)
VALUES (NEXTVAL('epayment.ep_pa_payable_line_id_seq'), 400, 0, CURRVAL('epayment.ep_gl_account_id_seq'),
        CURRVAL('epayment.ep_payment_advice_number_seq'), 2);

---

INSERT INTO epayment.ep_gl_account(id, code, creation_date_time)
VALUES (NEXTVAL('epayment.ep_gl_account_id_seq'), 'ACC3', NOW());

INSERT INTO epayment.ep_gl_account_description(id, language_code, "value", gl_account_id)
VALUES (NEXTVAL('epayment.ep_gl_account_description_id_seq'), 'en', 'Regular account 3',
        CURRVAL('epayment.ep_gl_account_id_seq'));

INSERT INTO epayment.ep_pa_bank_info(id, bank_account_number)
VALUES (NEXTVAL('epayment.ep_pa_bank_info_id_seq'), CURRVAL('epayment.ep_gl_account_id_seq'));

INSERT INTO epayment.ep_pa_bank_info_description(id, language_code, "value", pa_bank_info_id)
VALUES (NEXTVAL('epayment.ep_pa_bank_info_description_id_seq'), 'en', 'Bank 3',
        CURRVAL('epayment.ep_pa_bank_info_id_seq'));

INSERT INTO epayment.ep_payment_advice(id, drn, status, due_date, period_year, period_segment, creation_date_time, pan,
                                       taxpayer_id, pa_bank_info_id)
VALUES (NEXTVAL('epayment.ep_payment_advice_number_seq'), 'DRN3', 'INITIAL', CURRENT_DATE, CURRENT_DATE, CURRENT_DATE,
        CURRENT_DATE, 'PAN3', CURRVAL('epayment.ep_taxpayer_id_seq'), CURRVAL('epayment.ep_pa_bank_info_id_seq'));

INSERT INTO epayment.ep_pa_payable_line(id, amount, paid_amount, gl_account_id, payment_advice_id, el_transaction_id)
VALUES (NEXTVAL('epayment.ep_pa_payable_line_id_seq'), 240, 0, CURRVAL('epayment.ep_gl_account_id_seq'),
        CURRVAL('epayment.ep_payment_advice_number_seq'), 3);

