package com.teachingaura.controller

import com.teachingaura.api.API
import com.teachingaura.api.TestV2Details
import com.teachingaura.service.TestV2Service
import com.teachingaura.toApi
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping()
class TestV2Controller @Inject constructor(private val testV2Service: TestV2Service) : BaseController() {

    @GetMapping(API.GET_ALL_BATCH_TEST)
    suspend fun getAllTests(
        @PathVariable instituteId: String,
        @PathVariable batchId: String
    ): List<TestV2Details> {
        return testV2Service.getAllBatchTests(instituteId, batchId).map { it.toApi() }
    }


    @GetMapping(API.GET_BATCH_TEST_BY_ID)
    suspend fun getTestById(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable testId: String
    ): TestV2Details {
        return testV2Service.getBatchTestById(instituteId, batchId, testId).toApi()
    }

    @PostMapping(API.ADD_BATCH_TEST)
    suspend fun createTest(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @Valid @RequestBody testDetails: TestV2Details
    ): TestV2Details {
        return testV2Service.createBatchTest(instituteId, batchId, testDetails).toApi()
    }

    @PutMapping(API.UPDATE_BATCH_TEST)
    suspend fun updateTest(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable testId: String,
        @Valid @RequestBody testDetails: TestV2Details
    ): TestV2Details {
        return testV2Service.updateBatchTest(instituteId, batchId, testId, testDetails).toApi()
    }

    @DeleteMapping(API.DELETE_BATCH_TEST)
    suspend fun deleteTest(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable testId: String
    ): TestV2Details {
        return testV2Service.deleteBatchTest(instituteId, batchId, testId).toApi()
    }

}