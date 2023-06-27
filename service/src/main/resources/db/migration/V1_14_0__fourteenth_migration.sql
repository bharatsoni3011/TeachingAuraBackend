CREATE TABLE batch_test
(
    id            VARCHAR(255) NOT NULL,
    created_at    BIGINT NULL,
    modified_at   BIGINT NULL,
    name          TEXT NOT NULL,
    `description` TEXT NULL,
    start_time    BIGINT NULL,
    end_time      BIGINT NULL,
    course_id     VARCHAR(255) NOT NULL,
    batch_id      VARCHAR(255) NOT NULL,
    institute_id  VARCHAR(255) NOT NULL,
    sections TEXT,
    CONSTRAINT pk_test_v2 PRIMARY KEY (id)
);

ALTER TABLE batch_test
    ADD CONSTRAINT FK_TEST_V2_ON_BATCH FOREIGN KEY (batch_id) REFERENCES batch (id);

ALTER TABLE batch_test
    ADD CONSTRAINT FK_TEST_V2_ON_COURSE FOREIGN KEY (course_id) REFERENCES course (id);

ALTER TABLE batch_test
    ADD CONSTRAINT FK_TEST_V2_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

CREATE TABLE batch_test_submission
(
    id              VARCHAR(255) NOT NULL,
    created_at      BIGINT NULL,
    modified_at     BIGINT NULL,
    institute_id    VARCHAR(255) NOT NULL,
    batch_id        VARCHAR(255) NOT NULL,
    test_id         VARCHAR(255) NOT NULL,
    student_id      VARCHAR(255) NOT NULL,
    submission_time BIGINT NULL,
    marks           BIGINT NULL,
    CONSTRAINT pk_batch_test_submission PRIMARY KEY (id)
);

ALTER TABLE batch_test_submission
    ADD CONSTRAINT uc_batch_test_submission_id UNIQUE (id);

ALTER TABLE batch_test_submission
    ADD CONSTRAINT FK_BATCH_TEST_SUBMISSION_ON_BATCH FOREIGN KEY (batch_id) REFERENCES batch (id);

ALTER TABLE batch_test_submission
    ADD CONSTRAINT FK_BATCH_TEST_SUBMISSION_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE batch_test_submission
    ADD CONSTRAINT FK_BATCH_TEST_SUBMISSION_ON_STUDENT FOREIGN KEY (student_id) REFERENCES student (id);

ALTER TABLE batch_test_submission
    ADD CONSTRAINT FK_BATCH_TEST_SUBMISSION_ON_TEST FOREIGN KEY (test_id) REFERENCES batch_test (id);

CREATE TABLE batch_test_submission_test_attachment
(
    batch_test_submission_id VARCHAR(255) NOT NULL,
    testAttachments_id VARCHAR(255) NOT NULL
);

ALTER TABLE batch_test_submission_test_attachment ADD CONSTRAINT fk_btsta_on_batch_submission FOREIGN KEY (batch_test_submission_id) REFERENCES submission (id);

ALTER TABLE batch_test_submission_test_attachment ADD CONSTRAINT fk_btsta_on_test_attachment FOREIGN KEY (testAttachments_id) REFERENCES test_attachment (id);
