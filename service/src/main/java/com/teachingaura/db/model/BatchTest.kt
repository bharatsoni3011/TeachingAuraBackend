package com.teachingaura.db.model

import javax.persistence.*


@Entity
@Table(name = "batch_test")
class BatchTest(
    id: String,

    name: String = "",

    description: String,

    startTime: Long,

    endTime: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    val course: Course,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    val batch: Batch,

    institute: Institute,

    sections: String = ""
//    submission: List<SubmissionV2> = listOf()
) : BaseTest(id, name, description, startTime, endTime, institute, sections)