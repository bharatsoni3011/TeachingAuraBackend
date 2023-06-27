package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "topic_file")
class TopicFile  (

    id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    val topic: Topic,

    @Column(name = "order_index")
    val orderIndex: Int,

    @Column(name = "url", columnDefinition = "TEXT")
    val url: String,

) : BaseEntity(id)