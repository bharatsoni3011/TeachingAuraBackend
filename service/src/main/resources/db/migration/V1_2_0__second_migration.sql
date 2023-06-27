CREATE TABLE study_material
(
    id          VARCHAR(255) NOT NULL,
    created_at  BIGINT NULL,
    modified_at BIGINT NULL,
    name        VARCHAR(255) NULL,
    course_id   VARCHAR(255) NULL,
    CONSTRAINT pk_study_material PRIMARY KEY (id)
);

ALTER TABLE study_material ADD CONSTRAINT uc_study_material_id UNIQUE (id);

CREATE TABLE study_material_attachment
(
    study_material_id VARCHAR(255) NOT NULL,
    attachments_id    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_study_material_attachment PRIMARY KEY (study_material_id, attachments_id)
);

ALTER TABLE study_material_attachment ADD CONSTRAINT fk_stumatatt_on_attachment FOREIGN KEY (attachments_id) REFERENCES attachment (id);

ALTER TABLE study_material_attachment ADD CONSTRAINT fk_stumatatt_on_study_material FOREIGN KEY (study_material_id) REFERENCES study_material (id);

ALTER TABLE student_institutes ADD CONSTRAINT uc_student_institutes_student UNIQUE (student_id);

ALTER TABLE teacher_institutes ADD CONSTRAINT uc_teacher_institutes_teacher UNIQUE (teacher_id);

CREATE UNIQUE INDEX IX_pk_student_invites ON student_invites (student_id, institute_id);

CREATE UNIQUE INDEX IX_pk_teacher_invites ON teacher_invites (teacher_id, institute_id);
