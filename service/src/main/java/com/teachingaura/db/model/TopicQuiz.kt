package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "topic_quiz")
class TopicQuiz(

    id: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", referencedColumnName = "id")
    val topic: Topic,

    @Column(name = "order_index")
    val orderIndex: Int,

    @Column(name = "json", columnDefinition = "TEXT")
    val json: String,

) : BaseEntity(id)