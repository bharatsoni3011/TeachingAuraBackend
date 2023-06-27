package com.teachingaura.controller

import com.teachingaura.api.API.ADD_TEST
import com.teachingaura.api.API.DELETE_TEST
import com.teachingaura.api.API.GET_ALL_TEST
import com.teachingaura.api.API.GET_TEST_BY_ID
import com.teachingaura.api.API.UPDATE_TEST
import com.teachingaura.api.TestDetails
import com.teachingaura.service.TestService
import com.teachingaura.toApi
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class TestController @Inject constructor(private val testService: TestService) : BaseController() {

    @GetMapping(GET_ALL_TEST)
    suspend fun getAllTests(
        @PathVariable instituteId: String,
        @PathVariable batchId: String
    ): List<TestDetails> {
        return testService.getAllTests(instituteId, batchId).map { it.toApi() }
    }


    @GetMapping(GET_TEST_BY_ID)
    suspend fun getTestById(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable testId: String
    ): TestDetails {
        return testService.getTestById(instituteId, batchId, testId).toApi()
    }

    @PostMapping(ADD_TEST)
    suspend fun createTest(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @Valid @RequestBody testDetails: TestDetails
    ): TestDetails {
        return testService.createTest(instituteId, batchId, testDetails).toApi()
    }

    @PutMapping(UPDATE_TEST)
    suspend fun updateTest(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable testId: String,
        @Valid @RequestBody testDetails: TestDetails
    ): TestDetails {
        return testService.updateTest(instituteId, batchId, testId, testDetails).toApi()
    }

    @DeleteMapping(DELETE_TEST)
    suspend fun deleteTest(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable testId: String
    ): TestDetails {
        return testService.deleteTest(instituteId, batchId, testId).toApi()
    }

}