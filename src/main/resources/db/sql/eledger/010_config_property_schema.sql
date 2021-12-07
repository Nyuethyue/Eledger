-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_property
(
    id                bigint  NOT NULL,
    code              varchar NOT NULL,
    data_type_id      integer NOT NULL,
    value             varchar NOT NULL,
    start_of_validity date    NOT NULL,
    end_of_validity   date    NULL
);

ALTER TABLE eledger_config.el_property
    ADD CONSTRAINT pk_property PRIMARY KEY (id);

ALTER TABLE ONLY eledger_config.el_property
    ADD CONSTRAINT fk_property_data_type
        FOREIGN KEY (data_type_id)
            REFERENCES eledger_config.el_data_type (id);

CREATE SEQUENCE eledger_config.el_property_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_property.id;

-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE eledger_config.el_property_description
(
    id            bigint  NOT NULL,
    language_code varchar NOT NULL,
    value         varchar NULL,
    property_id   integer NOT NULL
);

ALTER TABLE eledger_config.el_property_description
    ADD CONSTRAINT pk_property_description PRIMARY KEY (id);

ALTER TABLE eledger_config.el_property_description
    ADD CONSTRAINT un_property_description_property_id_lng_code
        UNIQUE (property_id, language_code);

ALTER TABLE ONLY eledger_config.el_property_description
    ADD CONSTRAINT fk_property_description_property
        FOREIGN KEY (property_id)
            REFERENCES eledger_config.el_property (id);

CREATE INDEX IF NOT EXISTS fki_property_description_property
    ON eledger_config.el_property_description (property_id);

CREATE SEQUENCE eledger_config.el_property_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_property_description.id;

-----------------------------------------------------------------------------------------------------------------------------
