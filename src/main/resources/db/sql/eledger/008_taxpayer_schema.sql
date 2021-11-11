CREATE TABLE eledger.el_taxpayer
(
    id                 bigint    NOT NULL,
    tpn                varchar   NOT NULL,
    creation_date_time timestamp NOT NULL
);

ALTER TABLE eledger.el_taxpayer
    ADD CONSTRAINT pk_taxpayer
        PRIMARY KEY (id);

ALTER TABLE eledger.el_taxpayer
    ADD CONSTRAINT un_taxpayer_tpn UNIQUE (tpn);

CREATE SEQUENCE eledger.el_taxpayer_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger.el_taxpayer.id;
