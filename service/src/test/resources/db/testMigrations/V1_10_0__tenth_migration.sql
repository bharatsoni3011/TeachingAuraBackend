CREATE TABLE batch_announcements (
  announcements_id VARCHAR(255) NOT NULL,
  batch_id VARCHAR(255) NOT NULL,
  CONSTRAINT pk_batch_announcements PRIMARY KEY (announcements_id, batch_id)
);

ALTER TABLE batch_announcements ADD CONSTRAINT fk_batann_on_announcement FOREIGN KEY (announcements_id) REFERENCES announcements (id);

ALTER TABLE batch_announcements ADD CONSTRAINT fk_batann_on_batch FOREIGN KEY (batch_id) REFERENCES batch (id);