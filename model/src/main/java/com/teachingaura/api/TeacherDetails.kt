package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class TeacherDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String="",
    @field:NotBlank(message = "Name is a required field.")
    val name: String="",
    val email: String="",
    @field:NotBlank(message = "Contact Number is a required field.")
    val contactNumber: String="",
    val subject: String="",
    val profilePicURL: String = ""
)
