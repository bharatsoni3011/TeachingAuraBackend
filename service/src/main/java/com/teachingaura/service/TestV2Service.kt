package com.teachingaura.service

import com.teachingaura.api.TestV2Details
import com.teachingaura.db.model.BatchTest

interface TestV2Service {

    suspend fun getBatchTestById(instituteId: String, batchId: String, testId: String): BatchTest

    suspend fun  getAllBatchTests(instituteId: String, batchId: String): List<BatchTest>

    suspend fun createBatchTest(instituteId: String, batchId: String, testV2Details: TestV2Details): BatchTest

    suspend fun updateBatchTest(
        instituteId: String,
        batchId: String,
        testId: String,
        testV2Details: TestV2Details
    ): BatchTest

    suspend fun deleteBatchTest(instituteId: String, batchId: String, testId: String): BatchTest

}