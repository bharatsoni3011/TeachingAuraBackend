package com.teachingaura.db

import com.teachingaura.db.model.Teacher
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : CrudRepository<Teacher, String> {

    fun findByEmail(email: String): Teacher?

    fun findByPid(id: String): Teacher?

    fun findByContactNumber(phoneNumber: String): Teacher?
}