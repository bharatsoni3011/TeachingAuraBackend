package com.teachingaura.api

import com.teachingaura.api.API.DELETE_STUDENT
import com.teachingaura.api.API.GET_SCHEDULE
import com.teachingaura.api.API.GET_STUDENT_ANNOUNCEMENTS
import com.teachingaura.api.API.GET_STUDENT_ENROLLED_COURSES
import com.teachingaura.api.API.GET_STUDENT_INSTITUTES
import retrofit2.http.*


interface StudentService : StudentApi {

    @POST(API.CREATE_STUDENT)
    override suspend fun createStudent(@Body request: StudentDetails): StudentDetails

    @GET(API.GET_STUDENT)
    override suspend fun getById(@Path(API.STUDENT_ID) id: String): StudentDetails

    @GET(API.GET_STUDENTS)
    override suspend fun getStudents(): List<StudentDetails>

    @GET(API.GET_BATCHES)
    override suspend fun getStudentEnrolledBatches(@Path(API.STUDENT_ID) id: String): List<BatchDetails>

    @GET(GET_STUDENT_ANNOUNCEMENTS)
    override suspend fun getStudentAnnouncements(@Path(API.STUDENT_ID) id: String): List<NoticeDetails>

    @GET(GET_SCHEDULE)
    override suspend fun getStudentScheduleForTimeRange(
        @Path(API.STUDENT_ID) id: String,
        @Query("startTime") startTime: Long,
        @Query("endTime") endTime: Long
    ): List<ScheduleDetails>

    @GET(GET_STUDENT_INSTITUTES)
    override suspend fun getStudentInstitutes(@Path(API.STUDENT_ID) id: String): List<InstituteDetails>

    @DELETE(DELETE_STUDENT)
    override suspend fun deleteStudent(@Path(API.STUDENT_ID) id: String)

    @GET(GET_STUDENT_ENROLLED_COURSES)
    override suspend fun getStudentEnrolledCourses(
        @Path(API.STUDENT_ID) id: String
    ): List<CourseAndBatchDetails>

    @POST(API.UPDATE_STUDENT)
    override suspend fun updateStudent(@Path(API.STUDENT_ID) studentId: String,
                                       @Body studentDetails: StudentDetails): StudentDetails


}
