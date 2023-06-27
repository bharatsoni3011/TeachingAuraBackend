
ALTER TABLE batch_enrollment
    ADD CONSTRAINT uc_batch_enrollments_index UNIQUE (student_id, batch_id);

ALTER TABLE teacher_batch
    ADD CONSTRAINT uc_teacher_batch_index UNIQUE (teacher_id, batch_id);

ALTER TABLE student_institutes
    ADD CONSTRAINT uc_student_institutes_index UNIQUE (student_id, institute_id);

ALTER TABLE teacher_institutes
    ADD CONSTRAINT uc_teacher_institutes_index UNIQUE (teacher_id, institute_id);

ALTER TABLE batch_enrollment DROP CONSTRAINT uc_batch_enrollments_student;

ALTER TABLE teacher_batch DROP CONSTRAINT  uc_teacher_batch_teacher;

ALTER TABLE student_institutes DROP CONSTRAINT uc_student_institutes_student ;

ALTER TABLE teacher_institutes DROP CONSTRAINT  uc_teacher_institutes_teacher;
