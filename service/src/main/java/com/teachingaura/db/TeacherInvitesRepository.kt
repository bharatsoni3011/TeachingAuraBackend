package com.teachingaura.db

import com.teachingaura.db.model.TeacherInviteKey
import com.teachingaura.db.model.TeacherInvites
import org.springframework.data.repository.CrudRepository

interface TeacherInvitesRepository: CrudRepository<TeacherInvites, TeacherInviteKey> {

    fun findAllById_InstituteId(instituteId: String): List<TeacherInvites>

    fun findAllById_TeacherId(teacherId: String): List<TeacherInvites>

    fun findById_InstituteIdAndId_TeacherId(instituteId: String, teacherId: String): TeacherInvites

    fun deleteById_InstituteIdAndId_TeacherId(instituteId: String, teacherId: String)

}
