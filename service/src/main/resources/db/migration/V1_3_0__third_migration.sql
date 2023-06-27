CREATE TABLE teacher_batch
(
    batch_id   VARCHAR(255) NOT NULL,
    teacher_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_teacher_batch PRIMARY KEY (batch_id, teacher_id)
);

ALTER TABLE teacher_batch
    ADD CONSTRAINT uc_teacher_batch_teacher UNIQUE (teacher_id);

ALTER TABLE teacher_batch
    ADD CONSTRAINT fk_teabat_on_batch FOREIGN KEY (batch_id) REFERENCES batch (id);

ALTER TABLE teacher_batch
    ADD CONSTRAINT fk_teabat_on_teacher FOREIGN KEY (teacher_id) REFERENCES teacher (id);
