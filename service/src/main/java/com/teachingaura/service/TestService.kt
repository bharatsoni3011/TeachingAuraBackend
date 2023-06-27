package com.teachingaura.service

import com.teachingaura.api.TestDetails
import com.teachingaura.db.model.Test

interface TestService {

    suspend fun getTestById(instituteId: String, batchId: String, testId: String): Test

    suspend fun getAllTests(instituteId: String, batchId: String): List<Test>

    suspend fun createTest(instituteId: String, batchId: String, testRequest: TestDetails): Test

    suspend fun updateTest(
        instituteId: String,
        batchId: String,
        testId: String,
        testRequest: TestDetails
    ): Test

    suspend fun deleteTest(instituteId: String, batchId: String, testId: String): Test

    suspend fun findById(testId: String): Test

}