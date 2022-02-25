-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.ep_reconciliation_upload_file
(
    id                     bigint  NOT NULL,
    file_path              VARCHAR NOT NULL,
    bank_id                VARCHAR,
    status                 VARCHAR NOT NULL,
    user_name              VARCHAR NOT NULL,
    creation_date_time     timestamp NOT NULL
);

ALTER TABLE epayment.ep_reconciliation_upload_file
    ADD CONSTRAINT pk_ep_reconciliation_upload_file PRIMARY KEY (id);

CREATE SEQUENCE epayment.ep_reconciliation_upload_file_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_reconciliation_upload_file.id;
-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.ep_reconciliation_upload_record
(
    id                      bigint NOT NULL,
    reconciliation_upload_file_id   bigint NOT NULL,
    bank_transaction_number VARCHAR,
    bank_branch_code        VARCHAR,
    bank_processing_date    date,
    bank_amount             numeric(20, 2),
    deposit_number          VARCHAR NOT NULL,
    deposit_date_time       timestamp,
    deposit_amount          numeric(20, 2),
    deposit_status          VARCHAR,
    record_status           VARCHAR NOT NULL,
    creation_date_time      timestamp NOT NULL
);

ALTER TABLE epayment.ep_reconciliation_upload_record
    ADD CONSTRAINT pk_ep_reconciliation_upload_record PRIMARY KEY (id);

ALTER TABLE ONLY epayment.ep_reconciliation_upload_record
    ADD CONSTRAINT fk_ep_reconciliation_upload_record_ep_reconciliation_upload_file_id
        FOREIGN KEY (reconciliation_upload_file_id) REFERENCES epayment.ep_reconciliation_upload_file(id);

CREATE SEQUENCE epayment.ep_reconciliation_upload_record_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_reconciliation_upload_record.id;
-----------------------------------------------------------------------------------------------------------------------------