package com.teachingaura.db.model

import javax.persistence.*


@Entity
@Table(name = "submission")
class Submission(
        id: String,
        institute: Institute,
        batch: Batch,
        student: Student,
        submissionTime: Long,
        testAttachments: List<TestAttachment> = listOf(),
        marks: Long,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "test_id", referencedColumnName = "id")
        val test: Test) : BaseSubmission(id, institute, batch, student, submissionTime, testAttachments, marks)
