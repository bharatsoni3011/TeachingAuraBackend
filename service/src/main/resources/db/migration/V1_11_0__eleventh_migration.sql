CREATE TABLE topic_file (
  id VARCHAR(255) NOT NULL,
  created_at BIGINT NULL,
  modified_at BIGINT NULL,
  topic_id VARCHAR(255) NULL,
  url TEXT NULL,
  order_index INT NULL,
  CONSTRAINT pk_topic_file PRIMARY KEY (id)
);

ALTER TABLE topic_file ADD CONSTRAINT FK_TOPIC_FILE_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES topic (id);

CREATE TABLE topic_quiz (
  id VARCHAR(255) NOT NULL,
  created_at BIGINT NULL,
  modified_at BIGINT NULL,
  topic_id VARCHAR(255) NULL,
  json TEXT NULL,
  order_index INT NULL,
  CONSTRAINT pk_topic_quiz PRIMARY KEY (id)
);

ALTER TABLE topic_quiz ADD CONSTRAINT FK_TOPIC_QUIZ_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES topic (id);

CREATE TABLE topic_text (
  id VARCHAR(255) NOT NULL,
  created_at BIGINT NULL,
  modified_at BIGINT NULL,
  topic_id VARCHAR(255) NULL,
  text TEXT NULL,
  order_index INT NULL,
  CONSTRAINT pk_topic_text PRIMARY KEY (id)
);

ALTER TABLE topic_text ADD CONSTRAINT FK_TOPIC_TEXT_ON_TOPIC FOREIGN KEY (topic_id) REFERENCES topic (id);

ALTER TABLE course ADD end_date BIGINT NULL;

ALTER TABLE course ADD fee BIGINT NULL;

ALTER TABLE course ADD start_date BIGINT NULL;

ALTER TABLE course ADD sub_title TEXT NULL;

ALTER TABLE course ADD type TEXT NULL;

ALTER TABLE course ADD url TEXT NULL;