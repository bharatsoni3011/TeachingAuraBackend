package com.teachingaura.service

import com.teachingaura.api.SubjectDetails
import com.teachingaura.db.model.Course
import com.teachingaura.db.model.Institute
import com.teachingaura.db.model.Subject

interface SubjectService {

    suspend fun getSubjectById(id: String): Subject

    fun updateSubject(course: Course, subjectDetails: List<SubjectDetails>, subjects: MutableList<Subject>)

    suspend fun searchSubject(searchKeyword:String):List<Subject>
}