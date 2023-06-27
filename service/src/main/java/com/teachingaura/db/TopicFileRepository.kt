package com.teachingaura.db

import com.teachingaura.db.model.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface TopicFileRepository: CrudRepository<TopicFile, String> {

    fun findByTopicPid(topicId: String): List<TopicFile>
}