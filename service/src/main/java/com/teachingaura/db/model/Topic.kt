package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "topic")
class Topic(
    id:String,

    @Column(name = "name", columnDefinition = "TEXT")
    val name: String,

    @Column(name = "description", columnDefinition = "TEXT")
    val description: String,

    @Column(name = "order_index")
    val orderIndex: Int,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    val subject: Subject
) : BaseEntity(id)
