package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class CourseDetails(
        @field:NotBlank(message = "Id is a required field.")
        val id: String = "",
        @field:NotBlank(message = "Name is a required field.")
        val name: String = "",
        @field:NotBlank(message = "Description is a required field.")
        val description: String = "",
        val subjects: List<SubjectDetails> = listOf(),
        @field:NotBlank(message = "Institute Id is a required field.")
        val instituteId: String = "",
        val subTitle: String = "",
        val url: String = "",
        val type: CourseType = CourseType.OFFLINE,
        val fee: Long = 0L,
        val durationInMonths: Long = 0L,
)