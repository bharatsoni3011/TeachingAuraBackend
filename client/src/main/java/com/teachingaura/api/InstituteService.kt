package com.teachingaura.api

import retrofit2.http.*

interface InstituteService {
    @POST(API.CREATE_INSTITUTE)
    suspend fun createInstitute(@Body request: InstituteDetails): InstituteDetails

    @GET(API.GET_INSTITUTES)
    suspend fun getAllInstitutes(): List<InstituteDetails>

    @GET(API.GET_INSTITUTE_BY_ID)
    suspend fun getInstituteById(@Path(API.INSTITUTE_ID) instituteId: String): InstituteDetails

    @POST(API.CREATE_COURSE)
    suspend fun createCourse(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Body courseRequest: CourseDetails
    ): CourseDetails

    @POST(API.CREATE_SCHEDULE)
    suspend fun createSchedule(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Body scheduleDetails: ScheduleDetails
    ): ScheduleDetails

    @POST(API.ADD_STUDENT_TO_INSTITUTE)
    suspend fun addStudentToInstitute(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.STUDENT_ID) studentId: String
    ): StudentDetails

    @POST(API.ADD_TEACHER_TO_INSTITUTE)
    suspend fun addTeacherToInstitute(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.TEACHER_ID) teacherId: String
    ): TeacherDetails

    @POST(API.CREATE_BATCH)
    suspend fun createBatch(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Body batchDetails: BatchDetails
    ): BatchDetails

    @POST(API.ADD_STUDENT_TO_BATCH)
    suspend fun addStudentToBatch(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.STUDENT_ID) studentID: String,
        @Path(API.BATCH_ID) batchId: String
    ): StudentDetails

    @POST(API.ADD_TEACHER_TO_BATCH)
    suspend fun addTeacherToBatch(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String,
        @Path(API.TEACHER_ID) teacherId: String
    ): TeacherDetails

    @DELETE(API.REMOVE_TEACHER_FROM_INSTITUTE)
    suspend fun removeTeacherFromInstitute(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.TEACHER_ID) teacherId: String
    ): TeacherDetails

    @DELETE(API.REMOVE_STUDENT_FROM_INSTITUTE)
    suspend fun removeStudentFromInstitute(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.STUDENT_ID) studentId: String
    ): StudentDetails


    @GET(API.GET_INSTITUTE_OVERVIEW)
    suspend fun getInstituteOverview(@Path(API.INSTITUTE_ID) instituteId: String): Overview

    @GET(API.GET_BATCH_BY_ID)
    suspend fun getBatchById(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String
    ): BatchDetails

    @GET(API.GET_COURSE_BY_ID)
    suspend fun getCourseById(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.COURSE_ID) courseId: String
    ): CourseDetails

    @POST(API.UPLOAD_ATTACHMENT)
    suspend fun generateSignedUrlToUpload(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Body attachmentDetails: AttachmentDetails
    ): AttachmentDetails

    @GET(API.GET_ATTACHMENT)
    suspend fun getAttachmentById(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.ATTACHMENT_ID) attachmentId: String
    ): AttachmentDetails

    @POST(API.CREATE_COURSE_STUDY_MATERIAL)
    suspend fun createCourseStudyMaterial(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String,
        @Body studyMaterialDetails: StudyMaterialDetails
    ): StudyMaterialDetails

    @GET(API.GET_COURSE_STUDY_MATERIAL)
    suspend fun getCourseStudyMaterial(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.COURSE_ID) courseId: String
    ): List<StudyMaterialDetails>

    @POST(API.CREATE_ANNOUNCEMENT)
    suspend fun createAnnouncement(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Body noticeDetails: NoticeDetails
    ): NoticeDetails

    @GET(API.GET_ANNOUNCEMENT_BY_ID)
    suspend fun getAnnouncementById(@Path(API.INSTITUTE_ID) instituteId: String, @Path(API.NOTICE_ID) noticeId: String): NoticeDetails

    @GET(API.GET_ALL_TEST)
    suspend fun getAllTests(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String
    ): List<TestDetails>


    @GET(API.GET_TEST_BY_ID)
    suspend fun getTestById(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String,
        @Path(API.TEST_ID) testId: String
    ): TestDetails

    @POST(API.ADD_TEST)
    suspend fun createTest(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String,
        @Body testDetails: TestDetails
    ): TestDetails

    @PUT(API.UPDATE_TEST)
    suspend fun updateTest(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String,
        @Path(API.TEST_ID) testId: String,
        @Body testDetails: TestDetails
    ): TestDetails

    @DELETE(API.DELETE_TEST)
    suspend fun deleteTest(
        @Path(API.INSTITUTE_ID) instituteId: String,
        @Path(API.BATCH_ID) batchId: String,
        @Path(API.TEST_ID) testId: String
    ): TestDetails

    @GET(API.GET_STUDENT_TESTS)
    suspend fun getStudentTests(
        @Path(API.STUDENT_ID) studentId: String
    ): List<TestDetails>

    @GET(API.GET_TEACHER_TESTS)
    suspend fun getTeacherTests(
        @Path(API.TEACHER_ID) teacherId: String
    ): List<TestDetails>

}
