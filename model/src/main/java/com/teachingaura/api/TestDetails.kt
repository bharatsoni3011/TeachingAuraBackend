package com.teachingaura.api

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class  TestDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotBlank(message = "Name is a required field.")
    val name: String = "",
    @field:NotBlank(message = "Description is a required field.")
    val description: String = "",
    @field:NotNull(message = "Start Time is a required field.")
    val startTime: Long = 0L,
    @field:NotNull(message = "End Time is a required field.")
    val endTime: Long = 0L,
    @field:NotBlank(message = "Batch Id is a required field.")
    val batchId: String = "",
    val testAttachmentDetails: List<TestAttachmentDetails> = listOf(),
    val submissions: List<SubmissionDetails> = listOf()
)
