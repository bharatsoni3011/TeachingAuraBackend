package com.teachingaura.db

import com.teachingaura.db.model.TopicText
import org.springframework.data.repository.CrudRepository

interface TopicTextRepository: CrudRepository<TopicText, String> {

    fun findByTopicPid(topicId: String): List<TopicText>
}