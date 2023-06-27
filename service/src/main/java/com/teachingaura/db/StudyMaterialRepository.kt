package com.teachingaura.db

import com.teachingaura.db.model.StudyMaterial
import com.teachingaura.db.model.Subject
import com.teachingaura.db.model.TeacherInviteKey
import com.teachingaura.db.model.TeacherInvites
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface StudyMaterialRepository: CrudRepository<StudyMaterial, String> {

    @Query("select a from StudyMaterial  a where a.subject in ?1")
    fun findBySubjectIn(subjectId: List<Subject>): List<StudyMaterial>

    fun deleteBySubjectPid(subjectId: String)
}