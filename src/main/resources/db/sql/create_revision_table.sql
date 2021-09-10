CREATE TABLE public.revision
(
    id        bigint NOT NULL,
    timestamp bigint NOT NULL,
    username  character varying
);

ALTER TABLE public.revision
    ADD CONSTRAINT pk_revision
        PRIMARY KEY (id);

CREATE SEQUENCE revision_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY public.revision.id;
