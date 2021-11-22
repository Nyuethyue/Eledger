CREATE TABLE epayment.ep_taxpayer
(
    id                 bigint    NOT NULL,
    tpn                varchar   NOT NULL,
    name               varchar   NOT NULL,
    creation_date_time timestamp NOT NULL
);

ALTER TABLE epayment.ep_taxpayer
    ADD CONSTRAINT pk_taxpayer
        PRIMARY KEY (id);

ALTER TABLE epayment.ep_taxpayer
    ADD CONSTRAINT un_taxpayer_tpn UNIQUE (tpn);

CREATE SEQUENCE epayment.ep_taxpayer_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_taxpayer.id;
