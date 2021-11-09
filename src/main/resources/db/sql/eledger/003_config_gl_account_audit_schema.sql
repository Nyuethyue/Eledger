CREATE TABLE eledger_config.gl_account_aud
(
    revision_id                 bigint   NOT NULL,
    revision_end_id             bigint,
    rev_type                    smallint NOT NULL,
    id                          bigint   NOT NULL,
    code                        varchar,
    status                      varchar,
    creation_date_time          timestamp,
    last_modification_date_time timestamp,
    start_of_validity           timestamp,
    end_of_validity             timestamp,
    gl_account_last_part_id     bigint
);

ALTER TABLE eledger_config.gl_account_aud
    ADD CONSTRAINT pk_gl_account_aud
        PRIMARY KEY (id, revision_id);

ALTER TABLE eledger_config.gl_account_aud
    ADD CONSTRAINT fk_gl_account_aud_revision
        FOREIGN KEY (revision_id)
            REFERENCES public.revision (id)
                MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE eledger_config.gl_account_aud
    ADD CONSTRAINT fk_gl_account_aud_revision_end
        FOREIGN KEY (revision_end_id)
            REFERENCES public.revision (id);

CREATE TABLE eledger_config.gl_account_description_aud
(
    revision_id     bigint   NOT NULL,
    revision_end_id bigint,
    rev_type        smallint NOT NULL,
    id              bigint   NOT NULL,
    language_code   varchar,
    value           varchar,
    gl_account_id   integer
);

ALTER TABLE eledger_config.gl_account_description_aud
    ADD CONSTRAINT pk_gl_account_description_aud
        PRIMARY KEY (id, revision_id);

ALTER TABLE eledger_config.gl_account_description_aud
    ADD CONSTRAINT fk_gl_account_description_aud_revision
        FOREIGN KEY (revision_id)
            REFERENCES public.revision (id)
                MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE eledger_config.gl_account_description_aud
    ADD CONSTRAINT fk_gl_account_description_aud_revision_end
        FOREIGN KEY (revision_end_id)
            REFERENCES public.revision (id);



CREATE TABLE eledger_config.gl_account_part_aud
(
    revision_id                 bigint   NOT NULL,
    revision_end_id             bigint,
    rev_type                    smallint NOT NULL,
    id                          bigint   NOT NULL,
    parent_id                   bigint,
    code                        varchar,
    status                      varchar,
    creation_date_time          timestamp,
    last_modification_date_time timestamp,
    start_of_validity           timestamp,
    end_of_validity             timestamp,
    gl_account_part_type_id     integer
);

ALTER TABLE eledger_config.gl_account_part_aud
    ADD CONSTRAINT pk_gl_account_part_aud
        PRIMARY KEY (id, revision_id);

ALTER TABLE eledger_config.gl_account_part_aud
    ADD CONSTRAINT fk_gl_account_part_aud_revision
        FOREIGN KEY (revision_id)
            REFERENCES public.revision (id)
                MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE eledger_config.gl_account_part_aud
    ADD CONSTRAINT fk_gl_account_part_aud_revision_end
        FOREIGN KEY (revision_end_id)
            REFERENCES public.revision (id);

CREATE TABLE eledger_config.gl_account_part_description_aud
(
    revision_id        bigint   NOT NULL,
    revision_end_id    bigint,
    rev_type           smallint NOT NULL,
    id                 bigint,
    language_code      varchar,
    value              varchar,
    gl_account_part_id integer
);

ALTER TABLE eledger_config.gl_account_part_description_aud
    ADD CONSTRAINT pk_gl_account_part_description_aud
        PRIMARY KEY (id, revision_id);

ALTER TABLE eledger_config.gl_account_part_description_aud
    ADD CONSTRAINT fk_gl_account_part_description_aud_revision
        FOREIGN KEY (revision_id)
            REFERENCES public.revision (id)
                MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE eledger_config.gl_account_part_description_aud
    ADD CONSTRAINT fk_gl_account_part_description_aud_revision_end
        FOREIGN KEY (revision_end_id)
            REFERENCES public.revision (id);
