package com.teachingaura.api

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class SubmissionDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotNull(message = "Submission Time is a required field.")
    val submissionTime: Long = 0L,
    @field:NotBlank(message = "Student Id is a required field.")
    val studentId: String = "",
    @field:NotBlank(message = "Student Name is a required field.")
    val studentName: String = "",
    val marks: Long = 0L,
    @field:NotEmpty(message = "Test Attachment is a required field.")
    val testAttachmentDetails: List<TestAttachmentDetails> = listOf(),
)
