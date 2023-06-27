package com.teachingaura.db

import com.teachingaura.db.model.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface CourseRepository : CrudRepository<Course, String> {

    fun findByPid(id: String): Course?

    fun findByNameContaining(name: String): List<Course>

    @Query(
        value = "SELECT c.* FROM course c WHERE MATCH (c.tags) AGAINST (:tags IN NATURAL LANGUAGE MODE)",
        nativeQuery = true
    )
    fun findFullTextSearchByTags(@Param("tags") tags: String): List<Course>

}