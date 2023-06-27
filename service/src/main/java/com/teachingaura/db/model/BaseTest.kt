package com.teachingaura.db.model

import javax.persistence.*

@MappedSuperclass
class BaseTest(
    id: String,

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    var name: String = "",

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,

    @Column(name = "start_time")
    var startTime: Long,

    @Column(name = "end_time")
    var endTime: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute,

    @Column(name = "sections", columnDefinition = "TEXT")
    val sections: String = "",

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testV2")
//    val submission: List<SubmissionV2> = listOf()
): BaseEntity(id)
