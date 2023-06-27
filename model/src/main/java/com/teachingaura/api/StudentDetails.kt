package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class StudentDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotBlank(message = "Name is a required field.")
    val name: String = "",
    val email: String = "",
    @field:NotBlank(message = "Contact Number is a required field.")
    val contactNumber: String = "",
    val maxEducation: MaxEducation = MaxEducation.BELOW_MATRICULATE,
    val dateOfBirth: Long = 0L,
    val profilePicURL: String = ""
)
