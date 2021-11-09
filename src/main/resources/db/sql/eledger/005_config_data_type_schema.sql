-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.data_type
(
    id   integer NOT NULL,
    type varchar NOT NULL
);

ALTER TABLE eledger_config.data_type
    ADD CONSTRAINT pk_data_type PRIMARY KEY (id);

ALTER TABLE eledger_config.data_type
    ADD CONSTRAINT un_data_type_type UNIQUE (type);

-----------------------------------------------------------------------------------------------------------------------------
