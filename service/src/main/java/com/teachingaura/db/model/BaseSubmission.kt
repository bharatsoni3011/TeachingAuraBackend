package com.teachingaura.db.model

import javax.persistence.*

@MappedSuperclass
class BaseSubmission(

    id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    val batch: Batch,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    val student: Student,

    @Column(name = "submission_time")
    val submissionTime: Long,

    @OneToMany(fetch = FetchType.EAGER)
    val testAttachments: List<TestAttachment> = listOf(),

    @Column(name = "marks")
    val marks: Long,
) : BaseEntity(id)
