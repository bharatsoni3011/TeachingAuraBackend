package com.teachingaura.db

import com.teachingaura.db.model.StudentInviteKey
import com.teachingaura.db.model.StudentInvites
import org.springframework.data.repository.CrudRepository

interface StudentInvitesRepository : CrudRepository<StudentInvites, StudentInviteKey> {

    fun findAllById_InstituteId(instituteId: String): List<StudentInvites>

    fun findAllById_StudentId(studentId:String): List<StudentInvites>

    fun findById_InstituteIdAndId_StudentId(instituteId: String, studentId: String): StudentInvites

    fun deleteById_InstituteIdAndId_StudentId(instituteId: String, studentId: String)
}
