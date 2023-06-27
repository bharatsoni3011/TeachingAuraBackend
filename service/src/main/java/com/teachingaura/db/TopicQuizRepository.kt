package com.teachingaura.db

import com.teachingaura.db.model.TopicQuiz
import org.springframework.data.repository.CrudRepository

interface TopicQuizRepository: CrudRepository<TopicQuiz, String> {

    fun findByTopicPid(topicId: String): List<TopicQuiz>
}