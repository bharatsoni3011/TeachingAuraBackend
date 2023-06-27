package com.teachingaura.service

import com.teachingaura.api.SubmissionDetails
import com.teachingaura.db.model.BaseSubmission

interface SubmissionService {

    suspend fun getSubmission(studentId: String, testId: String, submissionId: String): BaseSubmission

    suspend fun addSubmission(
        studentId: String,
        testId: String,
        submissionDetails: SubmissionDetails
    ): BaseSubmission

    suspend fun getAllSubmissions(instituteId: String, testId: String): List<BaseSubmission>

}