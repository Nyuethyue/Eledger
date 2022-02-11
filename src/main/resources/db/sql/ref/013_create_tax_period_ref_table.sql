
-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ref.tax_period
(
    id                integer NOT NULL,
    code              varchar NOT NULL
);

ALTER TABLE ONLY ref.tax_period
    ADD CONSTRAINT pk_tax_period PRIMARY KEY (id);

ALTER TABLE ref.tax_period
    ADD CONSTRAINT un_tax_period_code
        UNIQUE (code);

CREATE SEQUENCE ref.tax_period_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.tax_period.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ref.tax_period_description
(
    id            		integer NOT NULL,
    language_code 		varchar NOT NULL,
    value         		varchar NULL,
	tax_period_id	integer NOT NULL
);

ALTER TABLE ONLY ref.tax_period_description
    ADD CONSTRAINT pk_tax_period_description PRIMARY KEY (id);

ALTER TABLE ONLY ref.tax_period_description
    ADD CONSTRAINT un_tax_period_description_tax_period_id_lng_code
        UNIQUE (tax_period_id, language_code);

ALTER TABLE ONLY ref.tax_period_description
    ADD CONSTRAINT fk_tax_period_description_tax_period
        FOREIGN KEY (tax_period_id)
            REFERENCES ref.tax_period (id);

CREATE INDEX IF NOT EXISTS fki_tax_period_description_tax_period
    ON ref.tax_period_description (tax_period_id);

CREATE SEQUENCE ref.tax_period_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.tax_period_description.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ref.tax_period_segment
(
	id       		integer NOT NULL,
    code 			varchar NOT NULL,
	tax_period_id	integer NOT NULL
);

ALTER TABLE ONLY ref.tax_period_segment
    ADD CONSTRAINT pk_tax_period_segment PRIMARY KEY (id);

ALTER TABLE ref.tax_period_segment
    ADD CONSTRAINT un_tax_period_segment_code
        UNIQUE (code);

ALTER TABLE ONLY ref.tax_period_segment
    ADD CONSTRAINT fk_tax_period_segment_tax_period
        FOREIGN KEY (tax_period_id)
            REFERENCES ref.tax_period (id);

CREATE INDEX IF NOT EXISTS fki_tax_period_segment_tax_period
    ON ref.tax_period_segment (tax_period_id);

CREATE SEQUENCE ref.tax_period_segment_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.tax_period_segment.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE ref.tax_period_segment_description
(
    id            		integer NOT NULL,
    language_code 		varchar NOT NULL,
    value         		varchar NULL,
	tax_period_segment_id	integer NOT NULL
);

ALTER TABLE ONLY ref.tax_period_segment_description
    ADD CONSTRAINT pk_tax_period_segment_description PRIMARY KEY (id);

ALTER TABLE ONLY ref.tax_period_segment_description
    ADD CONSTRAINT un_tax_period_segment_description_tax_period_sgnt_id_lng_code
        UNIQUE (tax_period_segment_id, language_code);

ALTER TABLE ONLY ref.tax_period_segment_description
    ADD CONSTRAINT fk_tax_period_segment_description_tax_period_segment
        FOREIGN KEY (tax_period_segment_id)
            REFERENCES ref.tax_period_segment (id);

CREATE INDEX IF NOT EXISTS fki_tax_period_segment_description_tax_period_segment
    ON ref.tax_period_segment_description (tax_period_segment_id);

CREATE SEQUENCE ref.tax_period_segment_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.tax_period_segment_description.id;

-----------------------------------------------------------------------------------------------------------------------------
