package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "study_material")
class StudyMaterial(
    id: String,

    @Column(name = "name", columnDefinition = "TEXT")
    val name: String,

    @OneToMany(fetch = FetchType.EAGER)
    val attachments: List<Attachment> = listOf(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    val subject: Subject,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", referencedColumnName = "id")
    val batch: Batch,

) : BaseEntity(id)