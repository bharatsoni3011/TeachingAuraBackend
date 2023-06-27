package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "announcements")
class Announcement(
    id: String,

    @Column(name = "content", columnDefinition = "TEXT")
    val content: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val announcementType: AnnouncementType = AnnouncementType.INSTITUTE_LEVEL,

    @ManyToMany(fetch = FetchType.EAGER)
    val attachments: List<Attachment> = listOf(),

    //TODO: add support for batch level notices as well by adding a relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute,

    @ManyToMany
    @JoinTable(
        name = "batch_announcements",
        joinColumns = [JoinColumn(name = "announcements_id")],
        inverseJoinColumns = [JoinColumn(name = "batch_id")]
    )
    val batches: List<Batch> = listOf(),

) : BaseEntity(id)
