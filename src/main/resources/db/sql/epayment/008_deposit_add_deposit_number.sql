-----------------------------------------------------------------------------------------------------------------------------
ALTER TABLE "deposit"
ADD deposit_number VARCHAR NOT null;

CREATE SEQUENCE epayment.deposit_number_seq
    INCREMENT BY 1
    MAXVALUE 999999
    MINVALUE 1
    START 1
    CACHE 1
    CYCLE;
-----------------------------------------------------------------------------------------------------------------------------