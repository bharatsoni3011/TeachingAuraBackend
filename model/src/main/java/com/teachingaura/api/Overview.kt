package com.teachingaura.api

data class Overview (
    val instituteId: String="",
    val instituteName: String = "",
    val instituteImageUrl: String = "",
    val numberOfStudents: Long=0L,
    val numberOfTeachers: Long=0L,
    val numberOfCourses: Long=0L,
    val pastLectures:Long=0L,
    val liveLectures:Long=0L,
    val upcomingLectures:Long=0L,
    val totalClassesTaken:Long=0L,
    val totalHoursTaken:Long=0L
)