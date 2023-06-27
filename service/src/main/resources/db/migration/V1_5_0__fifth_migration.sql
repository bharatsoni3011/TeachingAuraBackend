CREATE TABLE test (
  id VARCHAR(255) NOT NULL,
  created_at BIGINT NULL,
  modified_at BIGINT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  start_time BIGINT NULL,
  end_time BIGINT NULL,
  institute_id VARCHAR(255) NULL,
  batch_id VARCHAR(255) NULL,
  CONSTRAINT pk_test PRIMARY KEY (id)
);

ALTER TABLE test ADD CONSTRAINT uc_test_id UNIQUE (id);

ALTER TABLE test ADD CONSTRAINT FK_TEST_ON_BATCH FOREIGN KEY (batch_id) REFERENCES batch (id);

ALTER TABLE test ADD CONSTRAINT FK_TEST_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);



CREATE TABLE test_attachment (
  id VARCHAR(255) NOT NULL,
  created_at BIGINT NULL,
  modified_at BIGINT NULL,
  type_of_attachment VARCHAR(255) NULL,
  CONSTRAINT pk_test_attachment PRIMARY KEY (id)
);

ALTER TABLE test_attachment ADD CONSTRAINT uc_test_attachment_id UNIQUE (id);

CREATE TABLE test_attachment_attachment (
  test_attachment_id VARCHAR(255) NOT NULL,
  attachments_id VARCHAR(255) NOT NULL
);

ALTER TABLE test_attachment_attachment ADD CONSTRAINT fk_tesattatt_on_attachment FOREIGN KEY (attachments_id) REFERENCES attachment (id);

ALTER TABLE test_attachment_attachment ADD CONSTRAINT fk_tesattatt_on_test_attachment FOREIGN KEY (test_attachment_id) REFERENCES test_attachment (id);

CREATE TABLE test_test_attachment (
  test_id VARCHAR(255) NOT NULL,
  testAttachments_id VARCHAR(255) NOT NULL
);

ALTER TABLE test_test_attachment ADD CONSTRAINT fk_testesatt_on_test FOREIGN KEY (test_id) REFERENCES test (id);

ALTER TABLE test_test_attachment ADD CONSTRAINT fk_testesatt_on_test_attachment FOREIGN KEY (testAttachments_id) REFERENCES test_attachment (id);

CREATE TABLE submission (
  id VARCHAR(255) NOT NULL,
  created_at BIGINT NULL,
  modified_at BIGINT NULL,
  institute_id VARCHAR(255) NULL,
  batch_id VARCHAR(255) NULL,
  test_id VARCHAR(255) NULL,
  student_id VARCHAR(255) NULL,
  submission_time BIGINT NULL,
  marks BIGINT NULL,
  CONSTRAINT pk_submission PRIMARY KEY (id)
);

ALTER TABLE submission ADD CONSTRAINT uc_submission_id UNIQUE (id);

ALTER TABLE submission ADD CONSTRAINT FK_SUBMISSION_ON_BATCH FOREIGN KEY (batch_id) REFERENCES batch (id);

ALTER TABLE submission ADD CONSTRAINT FK_SUBMISSION_ON_INSTITUTE FOREIGN KEY (institute_id) REFERENCES institute (id);

ALTER TABLE submission ADD CONSTRAINT FK_SUBMISSION_ON_STUDENT FOREIGN KEY (student_id) REFERENCES student (id);

ALTER TABLE submission ADD CONSTRAINT FK_SUBMISSION_ON_TEST FOREIGN KEY (test_id) REFERENCES test (id);

CREATE TABLE submission_test_attachment (
  submission_id VARCHAR(255) NOT NULL,
  testAttachments_id VARCHAR(255) NOT NULL
);

ALTER TABLE submission_test_attachment ADD CONSTRAINT fk_subtesatt_on_submission FOREIGN KEY (submission_id) REFERENCES submission (id);

ALTER TABLE submission_test_attachment ADD CONSTRAINT fk_subtesatt_on_test_attachment FOREIGN KEY (testAttachments_id) REFERENCES test_attachment (id);

