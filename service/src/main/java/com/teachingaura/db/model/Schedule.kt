package com.teachingaura.db.model

import javax.persistence.*
import javax.print.DocFlavor.STRING

@Entity
@Table(name = "schedule")
class Schedule(
    id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    val teacher: Teacher?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    val batch: Batch,

    @Column(name = "topic", columnDefinition = "TEXT")
    val topic: String? = null,

    @Column(name = "start_time")
    val startTime: Long,

    @Column(name = "end_time")
    val endTime: Long, // add the call url if needed

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    val subject: Subject? = null,

    @Column(name = "join_url", columnDefinition = "TEXT")
    val joinUrl: String,

    @Column(name = "zoom_meeting_id", columnDefinition = "TEXT")
    val zoomMeetingId: String,

    @Column(name = "zoom_host_id", columnDefinition = "TEXT")
    val zoomHostId: String,

    @Column(name = "img_url", columnDefinition = "TEXT")
    val imgUrl: String? = ""

) : BaseEntity(id)
