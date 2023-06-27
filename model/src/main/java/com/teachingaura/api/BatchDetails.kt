package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class BatchDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotBlank(message = "Name is a required field.")
    val name: String = "",
    val startDate: Long = 0L,
    @field:NotBlank(message = "Course Id is a required field.")
    val courseId: String = "",
    @field:NotBlank(message = "Course Name is a required field.")
    val courseName: String = "",
    @field:NotBlank(message = "Institute Id is a required field.")
    val instituteId: String = "",
    val isActive: Boolean = false,
    val imageUrl: String? = "",
    val numberOfTeachers: Int = 0,
    val numberOfStudents: Int = 0
)
