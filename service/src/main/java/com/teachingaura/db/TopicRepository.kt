package com.teachingaura.db

import com.teachingaura.db.model.Topic
import org.springframework.data.repository.CrudRepository

interface TopicRepository : CrudRepository<Topic, String> {

    fun deleteBySubjectPid(subjectId: String)
    fun findByNameContaining(name: String): List<Topic>
    fun findByPid(id: String): Topic
}