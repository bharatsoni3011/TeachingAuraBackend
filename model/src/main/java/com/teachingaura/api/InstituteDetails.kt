package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class InstituteDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotBlank(message = "Institute Name is a required field.")
    val instituteName: String = "",
    val logo: String = "",
    val email: String = "",
    val ownerContactNumber: String = "",
    val aboutUs: String = "",
    val ownerName: String = "",
    val ownerEmail: String = "",
)
