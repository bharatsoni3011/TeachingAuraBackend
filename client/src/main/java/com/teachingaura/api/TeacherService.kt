package com.teachingaura.api

import retrofit2.http.Body
import retrofit2.http.POST


interface TeacherService {

    @POST(API.CREATE_TEACHER)
    suspend fun createTeacher(@Body request: TeacherDetails): TeacherDetails

}