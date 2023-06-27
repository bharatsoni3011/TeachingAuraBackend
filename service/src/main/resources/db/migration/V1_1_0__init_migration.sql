CREATE TABLE batch
(
    id           VARCHAR(255) NOT NULL,
    created_at   BIGINT       NULL,
    modified_at  BIGINT       NULL,
    name         VARCHAR(255) NULL,
    start_date   BIGINT       NULL,
    active       BIT(1)       NULL,
    batch_type   VARCHAR(255) NULL,
    course_id    VARCHAR(255) NULL,
    institute_id VARCHAR(255) NULL,
    CONSTRAINT pk_batch PRIMARY KEY (id)
);

CREATE TABLE batch_enrollment
(
    batch_id VARCHAR(255) NOT NULL,
    student_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_batch_enrollment PRIMARY KEY (batch_id, student_id)
);

CREATE TABLE course
(
    id           VARCHAR(255) NOT NULL,
    created_at   BIGINT       NULL,
    modified_at  BIGINT       NULL,
    name         VARCHAR(255) NULL,
    institute_id VARCHAR(255) NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE institute
(
    id             VARCHAR(255) NOT NULL,
    created_at     BIGINT       NULL,
    modified_at    BIGINT       NULL,
    name           VARCHAR(255) NULL,
    logo           VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    contact_number VARCHAR(255) NULL,
    CONSTRAINT pk_institute PRIMARY KEY (id)
);

CREATE TABLE schedule
(
    id              VARCHAR(255) NOT NULL,
    created_at      BIGINT NULL,
    modified_at     BIGINT NULL,
    teacher_id      VARCHAR(255) NULL,
    institute_id    VARCHAR(255) NULL,
    batch_id        VARCHAR(255) NULL,
    topic_id        VARCHAR(255) NULL,
    start_time      BIGINT NULL,
    end_time        BIGINT NULL,
    join_url        VARCHAR(255) NULL,
    zoom_meeting_id VARCHAR(255) NULL,
    zoom_host_id VARCHAR(255) NULL,
    CONSTRAINT pk_schedule PRIMARY KEY (id)
);

CREATE TABLE student
(
    id             VARCHAR(255) NOT NULL,
    created_at     BIGINT       NULL,
    modified_at    BIGINT       NULL,
    name           VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    contact_number VARCHAR(255) NULL,
    dob            BIGINT       NULL,
    max_education  VARCHAR(255) NULL,
    CONSTRAINT pk_student PRIMARY KEY (id)
);

CREATE TABLE subject
(
    id          VARCHAR(255) NOT NULL,
    created_at  BIGINT       NULL,
    modified_at BIGINT       NULL,
    name        VARCHAR(255) NULL,
    course_id   VARCHAR(255) NULL,
    CONSTRAINT pk_subject PRIMARY KEY (id)
);

CREATE TABLE teacher
(
    id             VARCHAR(255) NOT NULL,
    created_at     BIGINT       NULL,
    modified_at    BIGINT       NULL,
    name           VARCHAR(255) NULL,
    contact_number VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    subject        VARCHAR(255) NULL,
    CONSTRAINT pk_teacher PRIMARY KEY (id)
);

CREATE TABLE topic
(
    id            VARCHAR(255) NOT NULL,
    created_at    BIGINT       NULL,
    modified_at   BIGINT       NULL,
    name          VARCHAR(255) NULL,
    `description` VARCHAR(255) NULL,
    order_index   INT          NULL,
    subject_id    VARCHAR(255) NULL,
    CONSTRAINT pk_topic PRIMARY KEY (id)
);

CREATE TABLE announcements
(
    id           VARCHAR(255) NOT NULL,
    created_at   BIGINT       NULL,
    modified_at  BIGINT       NULL,
    content      VARCHAR(255) NULL,
    type         VARCHAR(255) NULL,
    institute_id VARCHAR(255) NULL,
    CONSTRAINT pk_announcements PRIMARY KEY (id)
);

CREATE TABLE announcements_attachment
(
    announcements_id VARCHAR(255) NOT NULL,
    attachments_id  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_announcements_attachments PRIMARY KEY (announcements_id, attachments_id)
);

CREATE TABLE attachment
(
    id              VARCHAR(255) NOT NULL,
    created_at      BIGINT       NULL,
    modified_at     BIGINT       NULL,
    url             VARCHAR(255) NULL,
    name            VARCHAR(255) NULL,
    attachment_type VARCHAR(255) NULL,
    institute_id    VARCHAR(255) NULL,
    CONSTRAINT pk_attachment PRIMARY KEY (id)
);

CREATE TABLE student_institutes
(
    institute_id VARCHAR(255) NOT NULL,
    student_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_student_institutes PRIMARY KEY (institute_id, student_id)
);

CREATE TABLE student_invites
(
    institute_id VARCHAR(255) NOT NULL,
    student_id   VARCHAR(255) NOT NULL,
    invite_type VARCHAR(255) NULL,
    CONSTRAINT pk_student_invites PRIMARY KEY (institute_id, student_id)
);

CREATE TABLE teacher_invites
(
    institute_id VARCHAR(255) NOT NULL,
    teacher_id   VARCHAR(255) NOT NULL,
    invite_type VARCHAR(255) NULL,
    CONSTRAINT pk_teacher_invites PRIMARY KEY (institute_id, teacher_id)
);

CREATE TABLE teacher_institutes
(
    institute_id VARCHAR(255) NOT NULL,
    teacher_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_teacher_institutes PRIMARY KEY (institute_id, teacher_id)
);

ALTER TABLE announcements
    ADD CONSTRAINT uc_announcements_id UNIQUE (id);

ALTER TABLE attachment
    ADD CONSTRAINT uc_attachment_id UNIQUE (id);

ALTER TABLE batch_enrollment
    ADD CONSTRAINT uc_batch_enrollments_student UNIQUE (student_id);

ALTER TABLE batch
    ADD CONSTRAINT uc_batch_id UNIQUE (id);

ALTER TABLE course
    ADD CONSTRAINT uc_course_id UNIQUE (id);

ALTER TABLE institute
    ADD CONSTRAINT uc_institute_id UNIQUE (id);

ALTER TABLE schedule
    ADD CONSTRAINT uc_schedule_id UNIQUE (id);

ALTER TABLE student
    ADD CONSTRAINT uc_student_id UNIQUE (id);

ALTER TABLE subject
    ADD CONSTRAINT uc_subject_id UNIQUE (id);

ALTER TABLE teacher
    ADD CONSTRAINT uc_teacher_id UNIQUE (id);

ALTER TABLE topic
    ADD CONSTRAINT uc_topic_id UNIQUE (id);

ALTER TABLE announcements
    ADD CONSTRAINT FK_ANNOUNCEMENTS_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE attachment
    ADD CONSTRAINT FK_ATTACHMENT_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE batch
    ADD CONSTRAINT FK_BATCH_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE batch
    ADD CONSTRAINT FK_BATCH_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE course
    ADD CONSTRAINT FK_COURSE_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_BATCH FOREIGN KEY (batch_id) REFERENCES batch (id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_TEACHER FOREIGN KEY (teacher_id) REFERENCES teacher (id);

ALTER TABLE schedule
    ADD CONSTRAINT FK_SCHEDULE_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES topic (id);

ALTER TABLE subject
    ADD CONSTRAINT FK_SUBJECT_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE topic
    ADD CONSTRAINT FK_TOPIC_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subject (id);

ALTER TABLE announcements_attachment
    ADD CONSTRAINT fk_annatt_on_announcement FOREIGN KEY (announcements_id) REFERENCES announcements (id);

ALTER TABLE announcements_attachment
    ADD CONSTRAINT fk_annatt_on_attachment FOREIGN KEY (attachments_id) REFERENCES attachment (id);

ALTER TABLE batch_enrollment
    ADD CONSTRAINT fk_batenr_on_batch FOREIGN KEY (batch_id) REFERENCES batch (id);

ALTER TABLE batch_enrollment
    ADD CONSTRAINT fk_batenr_on_student FOREIGN KEY (student_id) REFERENCES student (id);

ALTER TABLE student_institutes
    ADD CONSTRAINT fk_stuins_on_institute FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE student_institutes
    ADD CONSTRAINT fk_stuins_on_student FOREIGN KEY (student_id) REFERENCES student (id);

ALTER TABLE student_invites
    ADD CONSTRAINT fk_studentinvites_on_institute FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE student_invites
    ADD CONSTRAINT fk_studentinvites_on_student FOREIGN KEY (student_id) REFERENCES student (id);

ALTER TABLE teacher_invites
    ADD CONSTRAINT fk_teacherinvites_on_institute FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE teacher_invites
    ADD CONSTRAINT fk_teacherinvites_on_teacher FOREIGN KEY (teacher_id) REFERENCES teacher (id);

ALTER TABLE teacher_institutes
    ADD CONSTRAINT fk_teains_on_institute FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE teacher_institutes
    ADD CONSTRAINT fk_teains_on_teacher FOREIGN KEY (teacher_id) REFERENCES teacher (id);
