package com.teachingaura.db

import com.teachingaura.db.model.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface SubjectRepository : CrudRepository<Subject, String> {

    fun findByPid(id: String): Subject?

    fun findByNameContaining(name: String): List<Subject>

    fun deleteByCoursePid(courseId: String)
}