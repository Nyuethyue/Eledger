INSERT INTO "deposit" (id,payment_mode_id,bank_deposit_date,amount,status,creation_date_time)
VALUES (NEXTVAL('epayment.deposit_id_seq'), 1, NOW() ,123,'RECONCILED', NOW());