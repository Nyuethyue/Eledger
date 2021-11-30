------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.denomination
(
    id     bigint  NOT NULL,
    val   varchar NOT NULL
);

ALTER TABLE ref.denomination
    ADD CONSTRAINT pk_denomination
        PRIMARY KEY (id);

CREATE SEQUENCE ref.denomination_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.denomination.id;
------------------------------------------------------------------------------------------------------------------------
