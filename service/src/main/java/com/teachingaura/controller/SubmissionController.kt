package com.teachingaura.controller

import com.teachingaura.api.API.ADD_SUBMISSION
import com.teachingaura.api.API.GET_ALL_SUBMISSIONS
import com.teachingaura.api.API.GET_SUBMISSION
import com.teachingaura.api.SubmissionDetails
import com.teachingaura.roles.TeacherAndInstituteOnly
import com.teachingaura.service.SubmissionService
import com.teachingaura.toApi
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class SubmissionController @Inject constructor(private val submissionService: SubmissionService) : BaseController() {

    @GetMapping(GET_SUBMISSION)
    suspend fun getSubmission(
        @PathVariable studentId: String,
        @PathVariable testId: String,
        @PathVariable submissionId: String
    ): SubmissionDetails {
        return submissionService.getSubmission(studentId, testId, submissionId).toApi()
    }

    @PostMapping(ADD_SUBMISSION)
    suspend fun addSubmission(
        @PathVariable studentId: String,
        @PathVariable testId: String,
        @Valid @RequestBody submissionDetails: SubmissionDetails
    ): SubmissionDetails {
        return submissionService.addSubmission(studentId, testId, submissionDetails).toApi()
    }

    @GetMapping(GET_ALL_SUBMISSIONS)
    @TeacherAndInstituteOnly
    suspend fun getAllSubmissions(
        @PathVariable instituteId: String,
        @PathVariable testId: String,
    ): List<SubmissionDetails> {
        return submissionService.getAllSubmissions(instituteId, testId).map {  it.toApi() }
    }
}
