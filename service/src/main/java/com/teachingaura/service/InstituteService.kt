package com.teachingaura.service

import com.teachingaura.api.*
import com.teachingaura.db.model.*

interface InstituteService {
    suspend fun getInstituteById(id: String): Institute

    suspend fun getAllInstitutes(): List<Institute>

    suspend fun createInstitute(instituteRequest: InstituteDetails): Institute

    suspend fun createCourse(instituteId: String, courseRequest: CourseDetails): Course

    suspend fun deleteCourse(instituteId: String, courseId: String): Unit

    suspend fun createBatch(instituteId: String, batchDetails: BatchDetails): Batch

    suspend fun createSchedule(scheduleDetails: ScheduleDetails): Schedule

    suspend fun addStudentToInstitute(instituteId: String, studentId: String): Student

    suspend fun addTeacherToInstitute(instituteId: String, studentID: String): Teacher

    suspend fun addStudentToBatch(instituteId: String, batchId: String, studentID: String): Student

    suspend fun removeTeacherFromInstitute(instituteId: String, teacherId: String): Teacher

    suspend fun removeStudentFromInstitute(instituteId: String, teacherId: String): Student

    suspend fun getSchedules(instituteId: String): List<Schedule>

    suspend fun getPastSchedules(instituteId: String): List<Schedule>

    suspend fun getLiveSchedules(instituteId: String): List<Schedule>

    suspend fun getUpcomingSchedules(instituteId: String): List<Schedule>

    suspend fun getTotalHours(instituteId: String): Long

    suspend fun sendInviteToStudent(instituteId: String, studentId: String): Student

    suspend fun sendInviteToStudent(instituteId: String, student: StudentDetails): Student

    suspend fun sendInviteToTeacher(instituteId: String, teacherId: String): Teacher

    suspend fun sendInviteToTeacher(instituteId: String, teacherDetails: TeacherDetails): Teacher

    suspend fun getStudentInvites(instituteId: String): List<StudentInvites>

    suspend fun getTeacherInvites(instituteId: String): List<TeacherInvites>

    suspend fun updateInstitute(instituteId: String, instituteRequest: InstituteDetails): Institute

    suspend fun getInstituteTeachers(instituteId: String): List<Teacher>

    suspend fun getInstituteStudents(instituteId: String): List<Student>

    suspend fun getInstituteCourses(instituteId: String): List<Course>

    suspend fun getInstituteBatches(instituteId: String): List<Batch>

    suspend fun getBatchById(instituteId: String, batchId: String): Batch

    suspend fun getCourseById(instituteId: String, courseId: String): Course

    suspend fun updateCourse(instituteId: String, courseId: String, courseRequest: CourseDetails): Course

    suspend fun updateSubjects(instituteId: String, courseId: String, courseRequest: CourseDetails): Course

    suspend fun getCourseStudyMaterial(instituteId: String, courseId: String): List<StudyMaterial>

    suspend fun getInstituteAnnouncements(instituteId: String): List<Announcement>

    suspend fun createAnnouncement(instituteId: String, announcement: AnnouncementDetails): Announcement

    suspend fun getAnnouncementById(instituteId: String, noticeId: String): Announcement

    suspend fun deleteAllInstituteAnnouncements(id: String): Institute

    suspend fun createCourseStudyMaterial(
        instituteId: String,
        batchId: String,
        studyMaterialDetails: StudyMaterialDetails
    ): StudyMaterial

    suspend fun getScheduleById(scheduleId: String): Schedule

    suspend fun getInstituteSchedules(instituteId: String): List<Schedule>

    suspend fun getAllSchedules(): List<Schedule>

    suspend fun addTeacherToBatch(instituteId: String, batchId: String, teacherId: String): Teacher

    suspend fun removeTeacherFromBatch(instituteId: String, batchId: String, teacherId: String): Teacher

    suspend fun deleteAllInstituteAttachments(id: String): Institute

    suspend fun getAllBatchStudents(instituteId: String, batchId: String): Set<Student>

    suspend fun getAllBatchTeachers(instituteId: String, batchId: String): Set<Teacher>

    suspend fun getBatchStudyMaterials(instituteId: String, batchId: String): List<StudyMaterial>

    suspend fun removeStudentFromBatch(instituteId: String, batchId: String, studentId: String): Student

    suspend fun getStudentBatches(instituteId: String, studentId: String): List<Batch>

    suspend fun getZoomUrlById(meetingId: Long): String

    suspend fun getTeacherBatches(instituteId: String, teacherId: String): List<Batch>

    suspend fun addTopicDetails(
        instituteId: String,
        courseId: String,
        topicId: String,
        topicAddDetails: TopicAddDetails
    ): TopicAddDetails

    suspend fun getTopicDetails(
        instituteId: String,
        courseId: String,
        topicId: String
    ): TopicAddDetails

    suspend fun createCourseV2(instituteId: String, courseV2Request: CourseDetails): Course


    suspend fun getUserInstituteAndRole(id: String): Set<UserRoleDetails>

    suspend fun searchCourse(searchKeyword: String): Set<Course>

    suspend fun searchInstitute(searchKeyword: String): Set<Institute>
}
