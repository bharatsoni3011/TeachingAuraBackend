ALTER TABLE course DROP COLUMN start_date;

ALTER TABLE course DROP COLUMN end_date;

ALTER TABLE course ADD duration_in_months BIGINT NOT NULL default 0;
