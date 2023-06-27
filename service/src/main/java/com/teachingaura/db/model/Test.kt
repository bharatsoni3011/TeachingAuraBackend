package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "test")
class Test(

    id: String,

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    var name: String = "",

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String,

    @Column(name = "start_time")
    var startTime: Long,

    @Column(name = "end_time")
    var endTime: Long,

    @OneToMany(fetch = FetchType.EAGER)
    var testAttachments: MutableList<TestAttachment> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    val batch: Batch,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
    val submissions: List<Submission> = listOf(),

    ) : BaseEntity(id)