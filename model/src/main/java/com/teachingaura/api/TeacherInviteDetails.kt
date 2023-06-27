package com.teachingaura.api

data class TeacherInviteDetails(
    val institute: InstituteDetails = InstituteDetails(),
    val teacher: TeacherDetails = TeacherDetails(),
    val status: InviteType = InviteType.INVITED
)
