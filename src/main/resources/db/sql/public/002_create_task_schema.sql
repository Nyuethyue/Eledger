CREATE TABLE task
(
    id                bigint    NOT NULL,
    uuid              varchar   NOT NULL,
    task_type         varchar   NOT NULL,
    payload           text      NULL,
    trigger_count     integer   NOT NULL,
    created_time      timestamp NOT NULL,
    completed_time    timestamp NULL,
    originator_id     varchar   NULL,
    originator_name   varchar   NULL,
    creation_source   varchar   NULL,
    initiator_task_id varchar   NULL
);

ALTER TABLE task
    ADD CONSTRAINT pk_ep_rma_message PRIMARY KEY (id);
