package com.teachingaura.api

data class StudentInviteDetails(
    val institute: InstituteDetails = InstituteDetails(),
    val student: StudentDetails = StudentDetails(),
    val status: InviteType = InviteType.INVITED
)
