package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class ScheduleDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotBlank(message = "Institute Id is a required field.")
    val instituteId: String = "",
    @field:NotBlank(message = "Batch Id is a required field.")
    val batchId: String = "",
    @field:NotBlank(message = "Course Id is a required field.")
    val courseId: String="",
    @field:NotBlank(message = "Teacher Id is a required field.")
    val teacherId: String? = null,
    @field:NotBlank(message = "Topic Name is a required field.")
    val topicName: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val subjectId: String? = "",
    val subjectName: String = "",
    // this will be set for teachers and institutes
    // this will be set for teachers and institutes
    val joinUrl: String? = null,
    val meetingId: String = "",
    val imgUrl: String? = ""
)
