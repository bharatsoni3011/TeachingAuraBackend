package com.teachingaura.api

data class StudentSignupRequest(
    val name: String = "",
    val contactNumber: String = "",
    val email: String = "",
    val maxEducation: MaxEducation = MaxEducation.BELOW_MATRICULATE,
    val dateOfBirth: Long = 0L
)
