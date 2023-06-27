package com.teachingaura.api

interface StudentApi {

    suspend fun getStudents(): List<StudentDetails>

    suspend fun getById(studentId: String): StudentDetails

    suspend fun createStudent(request: StudentDetails): StudentDetails

    suspend fun getStudentInvites(studentId: String): List<StudentInviteDetails>

    suspend fun acceptInstituteInvite(studentId: String, instituteId: String): StudentInviteDetails

    suspend fun rejectInstituteInvite(studentId: String, instituteId: String): StudentInviteDetails

    suspend fun getStudentEnrolledBatches(studentId: String): List<BatchDetails>

    suspend fun getStudentAnnouncements(studentId: String): List<NoticeDetails>

    suspend fun getStudentScheduleForTimeRange(
        studentId: String,
        startTime: Long,
        endTime: Long
    ): List<ScheduleDetails>

    suspend fun getStudentInstitutes(studentId: String): List<InstituteDetails>

    suspend fun deleteStudent(studentId: String)

    suspend fun getStudentEnrolledCourses(
        studentId: String
    ): List<CourseAndBatchDetails>

    suspend fun updateStudent(studentId: String, studentDetails: StudentDetails): StudentDetails
}
