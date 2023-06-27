package com.teachingaura.db

import com.teachingaura.db.model.Student
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : CrudRepository<Student, String> {

    fun findByPid(id: String): Student?

    fun findByEmail(email: String): Student?

    fun findByContactNumber(phoneNumber: String): Student?

}