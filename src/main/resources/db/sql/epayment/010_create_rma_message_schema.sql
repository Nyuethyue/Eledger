-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE epayment.ep_rma_message_ar_part
(
    id                 bigint         NOT NULL,
    msg_type           varchar        NOT NULL,
    benf_txn_time      timestamp      NOT NULL,
    order_no           varchar        NOT NULL,
    benf_id            varchar        NOT NULL,
    benf_bank_code     varchar        NOT NULL,
    txn_currency       varchar        NOT NULL,
    txn_amount         numeric(16, 2) NOT NULL,
    remitter_email     varchar,
    checksum           varchar        NOT NULL,
    payment_desc       varchar        NOT NULL,
    bfs_version        numeric(5, 2)  NOT NULL,
    creation_date_time timestamp      NOT NULL
);

ALTER TABLE epayment.ep_rma_message_ar_part
    ADD CONSTRAINT pk_ep_rma_message_ar_part PRIMARY KEY (id);

CREATE SEQUENCE epayment.ep_rma_message_ar_part_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_rma_message_ar_part.id;


-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE epayment.ep_rma_message_as_part
(
    id                 bigint         NOT NULL,
    msg_type           varchar        NOT NULL,
    benf_txn_time      timestamp      NOT NULL,
    order_no           varchar        NOT NULL,
    benf_id            varchar        NOT NULL,
    benf_bank_code     varchar        NOT NULL,
    txn_currency       varchar        NOT NULL,
    txn_amount         numeric(16, 2) NOT NULL,
    remitter_email     varchar,
    checksum           varchar        NOT NULL,
    payment_desc       varchar        NOT NULL,
    bfs_version        numeric(5, 2)  NOT NULL,
    creation_date_time timestamp      NOT NULL
);

ALTER TABLE epayment.ep_rma_message_as_part
    ADD CONSTRAINT pk_ep_rma_message_as_part PRIMARY KEY (id);

CREATE SEQUENCE epayment.ep_rma_message_as_part_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_rma_message_as_part.id;


-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE epayment.ep_rma_message
(
    id                     bigint    NOT NULL,
    order_no               varchar   NOT NULL,
    status                 varchar   NOT NULL,
    payment_advice_id      bigint    NOT NULL,
    rma_message_ar_part_id bigint    NOT NULL,
    rma_message_as_part_id bigint,
    creation_date_time     timestamp NOT NULL
);


ALTER TABLE epayment.ep_rma_message
    ADD CONSTRAINT pk_ep_rma_message PRIMARY KEY (id);

ALTER TABLE ONLY epayment.ep_rma_message
    ADD CONSTRAINT fk_payment_advice
        FOREIGN KEY (payment_advice_id) REFERENCES epayment.ep_payment_advice (id);

ALTER TABLE ONLY epayment.ep_rma_message
    ADD CONSTRAINT fk_rma_message_ar_part
        FOREIGN KEY (rma_message_ar_part_id) REFERENCES epayment.ep_rma_message_ar_part (id);

ALTER TABLE ONLY epayment.ep_rma_message
    ADD CONSTRAINT fk_rma_message_as_part
        FOREIGN KEY (rma_message_as_part_id) REFERENCES epayment.ep_rma_message_as_part (id);

ALTER TABLE epayment.ep_rma_message
    ADD CONSTRAINT un_rma_message_order_no UNIQUE (order_no);


CREATE SEQUENCE epayment.ep_rma_message_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_rma_message.id;


-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE epayment.ep_rma_message_response
(
    id                 bigint         NOT NULL,
    msg_type           varchar        NOT NULL,
    bfs_txn_id         varchar,
    bfs_txn_time       timestamp,
    benf_txn_time      timestamp      NOT NULL,
    order_no           varchar        NOT NULL,
    benf_id            varchar        NOT NULL,
    txn_currency       varchar        NOT NULL,
    txn_amount         numeric(16, 2) NOT NULL,
    checksum           varchar        NOT NULL,
    remitter_name      varchar,
    remitter_bank_id   varchar,
    debit_auth_code    varchar,
    debit_auth_no      varchar,
    creation_date_time timestamp      NOT NULL,
    rma_message_id     bigint         NOT NULL
);

ALTER TABLE epayment.ep_rma_message_response
    ADD CONSTRAINT pk_ep_rma_message_response PRIMARY KEY (id);

ALTER TABLE ONLY epayment.ep_rma_message_response
    ADD CONSTRAINT fk_rma_message_response_rma_message
        FOREIGN KEY (rma_message_id) REFERENCES epayment.ep_rma_message (id);

CREATE INDEX IF NOT EXISTS fki_rma_message_response_rma_message
    ON epayment.ep_rma_message_response (rma_message_id);

CREATE SEQUENCE epayment.ep_rma_message_response_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_rma_message_response.id;
