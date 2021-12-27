-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.reconciliation_upload_file
(
    id                     bigint  NOT NULL,
    file_path              VARCHAR NOT NULL,
    bank_id                VARCHAR NOT NULL,
    status                 VARCHAR NOT NULL,
    creation_date_time     timestamp NOT NULL
);

ALTER TABLE epayment.reconciliation_upload_file
    ADD CONSTRAINT pk_reconciliation_upload_file PRIMARY KEY (id);

CREATE SEQUENCE epayment.reconciliation_upload_file_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.reconciliation_upload_file.id;
-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.reconciliation_upload_record
(
    id                     bigint NOT NULL,
    upload_id              bigint NOT NULL,
    bank_id                VARCHAR NOT NULL,
    status                 VARCHAR NOT NULL,
    creation_date_time     timestamp NOT NULL
);

ALTER TABLE epayment.reconciliation_upload_record
    ADD CONSTRAINT pk_reconciliation_upload_record PRIMARY KEY (id);

ALTER TABLE ONLY epayment.reconciliation_upload_record
    ADD CONSTRAINT fk_reconciliation_upload_record_upload_id
        FOREIGN KEY (upload_id) REFERENCES epayment.deposit (id);

ALTER TABLE ONLY epayment.deposit_receipt
    ADD CONSTRAINT fk_deposit_receipt_id
        FOREIGN KEY (receipt_id) REFERENCES epayment.reconciliation_upload_file (id);


CREATE SEQUENCE epayment.reconciliation_upload_record_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.reconciliation_upload_record.id;
-----------------------------------------------------------------------------------------------------------------------------