package com.teachingaura.controller

import com.teachingaura.api.*
import com.teachingaura.api.API.ADD_LIST_OF_STUDENTS_TO_BATCH
import com.teachingaura.api.API.ADD_LIST_OF_TEACHERS_TO_BATCH
import com.teachingaura.api.API.ADD_STUDENT_TO_BATCH
import com.teachingaura.api.API.ADD_STUDENT_TO_INSTITUTE
import com.teachingaura.api.API.ADD_TEACHER_TO_BATCH
import com.teachingaura.api.API.ADD_TEACHER_TO_INSTITUTE
import com.teachingaura.api.API.ADD_TOPIC_DETAILS
import com.teachingaura.api.API.CREATE_ANNOUNCEMENT
import com.teachingaura.api.API.CREATE_BATCH
import com.teachingaura.api.API.CREATE_COURSE
import com.teachingaura.api.API.CREATE_COURSE_STUDY_MATERIAL
import com.teachingaura.api.API.CREATE_COURSE_V2
import com.teachingaura.api.API.CREATE_INSTITUTE
import com.teachingaura.api.API.CREATE_SCHEDULE
import com.teachingaura.api.API.DELETE_ALL_INSTITUTE_ANNOUNCEMENTS
import com.teachingaura.api.API.DELETE_ALL_INSTITUTE_ATTACHMENTS
import com.teachingaura.api.API.DELETE_COURSE
import com.teachingaura.api.API.GET_ALL_BATCH_STUDENTS
import com.teachingaura.api.API.GET_ALL_BATCH_TEACHERS
import com.teachingaura.api.API.GET_ANNOUNCEMENT_BY_ID
import com.teachingaura.api.API.GET_BATCH_BY_ID
import com.teachingaura.api.API.GET_BATCH_STUDY_MATERIALS
import com.teachingaura.api.API.GET_COURSE_BY_ID
import com.teachingaura.api.API.GET_COURSE_BY_ID_V2
import com.teachingaura.api.API.GET_COURSE_BY_NAME
import com.teachingaura.api.API.GET_COURSE_STUDY_MATERIAL
import com.teachingaura.api.API.GET_INSTITUTES
import com.teachingaura.api.API.GET_INSTITUTE_AND_ROLE_BY_ID
import com.teachingaura.api.API.GET_INSTITUTE_BATCHES
import com.teachingaura.api.API.GET_INSTITUTE_BY_ID
import com.teachingaura.api.API.GET_INSTITUTE_BY_NAME
import com.teachingaura.api.API.GET_INSTITUTE_COURSES
import com.teachingaura.api.API.GET_INSTITUTE_OVERVIEW
import com.teachingaura.api.API.GET_INSTITUTE_STUDENTS
import com.teachingaura.api.API.GET_INSTITUTE_TEACHERS
import com.teachingaura.api.API.GET_SCHEDULES
import com.teachingaura.api.API.GET_SCHEDULE_BY_ID
import com.teachingaura.api.API.GET_STUDENT_BATCHES_OF_INSTITUTE
import com.teachingaura.api.API.GET_STUDENT_INVITES
import com.teachingaura.api.API.GET_TEACHER_INVITES
import com.teachingaura.api.API.GET_TOPIC_DETAILS
import com.teachingaura.api.API.GET_ZOOM_HOST_URL_BY_MEETING_ID
import com.teachingaura.api.API.INSTITUTE_GET_ANNOUNCEMENTS
import com.teachingaura.api.API.INSTITUTE_GET_SCHEDULES
import com.teachingaura.api.API.INVITE_STUDENT_TO_INSTITUTE
import com.teachingaura.api.API.INVITE_STUDENT_TO_INSTITUTE_V2
import com.teachingaura.api.API.INVITE_TEACHER_TO_INSTITUTE
import com.teachingaura.api.API.INVITE_TEACHER_TO_INSTITUTE_V2
import com.teachingaura.api.API.REMOVE_STUDENT_FROM_BATCH
import com.teachingaura.api.API.REMOVE_STUDENT_FROM_INSTITUTE
import com.teachingaura.api.API.REMOVE_TEACHER_FROM_BATCH
import com.teachingaura.api.API.REMOVE_TEACHER_FROM_INSTITUTE
import com.teachingaura.api.API.UPDATE_COURSE
import com.teachingaura.api.API.UPDATE_COURSE_SUBJECTS
import com.teachingaura.api.API.UPDATE_INSTITUTE
import com.teachingaura.roles.InstituteOnly
import com.teachingaura.roles.Role
import com.teachingaura.roles.RoleService
import com.teachingaura.roles.TeacherAndInstituteOnly
import com.teachingaura.service.InstituteService
import com.teachingaura.toApi
import com.teachingaura.toCourseAndBatchApi
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class InstituteController @Inject constructor(private val instituteService: InstituteService,
                                              private val roleService: RoleService
) : BaseController() {

    @PostMapping(CREATE_INSTITUTE)
    suspend fun createInstitute(@Valid @RequestBody request: InstituteDetails): InstituteDetails {
        val institute = instituteService.createInstitute(request).toApi()
        roleService.addRoleToUser(request.id, Role.ROLE_INSTITUTE)
        return institute
    }

    @GetMapping(GET_INSTITUTES)
    suspend fun getAllInstitutes(): List<InstituteDetails>? {
        return instituteService.getAllInstitutes()
            .map { it.toApi() }
            .toList()
    }

    @GetMapping(GET_INSTITUTE_BY_ID)
    suspend fun getInstituteById(@PathVariable instituteId: String): InstituteDetails {
        return instituteService.getInstituteById(instituteId).toApi()
    }

    @PostMapping(CREATE_COURSE)
    @InstituteOnly
    suspend fun createCourse(
        @PathVariable instituteId: String,
        @Valid  @RequestBody courseRequest: CourseDetails
    ): CourseDetails {
        return instituteService.createCourse(instituteId, courseRequest).toApi()
    }

    @DeleteMapping(DELETE_COURSE)
    @InstituteOnly
    suspend fun deleteCourse(
        @PathVariable instituteId: String,
        @PathVariable courseId: String,
    ): Unit {
        return instituteService.deleteCourse(instituteId, courseId)
    }


    @PostMapping(CREATE_SCHEDULE)
    @TeacherAndInstituteOnly
    suspend fun createSchedule(
        @PathVariable instituteId: String,
        @Valid  @RequestBody scheduleDetails: ScheduleDetails
    ): ScheduleDetails {
        return instituteService.createSchedule(scheduleDetails).toApi()
    }


    @PostMapping(ADD_STUDENT_TO_INSTITUTE)
    @InstituteOnly
    suspend fun addStudentToInstitute(
        @PathVariable instituteId: String,
        @PathVariable studentId: String
    ): StudentDetails {
        return instituteService.addStudentToInstitute(instituteId, studentId).toApi()
    }

    @DeleteMapping(REMOVE_STUDENT_FROM_INSTITUTE)
    @InstituteOnly
    suspend fun removeStudentFromInstitute(
        @PathVariable instituteId: String,
        @PathVariable studentId: String
    ): StudentDetails {
        return instituteService.removeStudentFromInstitute(instituteId, studentId).toApi()
    }

    @PostMapping(ADD_TEACHER_TO_INSTITUTE)
    @InstituteOnly
    suspend fun addTeacherToInstitute(
        @PathVariable instituteId: String,
        @PathVariable teacherId: String
    ): TeacherDetails {
        return instituteService.addTeacherToInstitute(instituteId, teacherId).toApi()
    }

    @PostMapping(ADD_LIST_OF_TEACHERS_TO_BATCH)
    @InstituteOnly
    suspend fun addListOfTeachersToBatch(@PathVariable instituteId: String,@PathVariable batchId: String,@RequestBody listOfTeacherIds:List<String>):List<TeacherDetails>{
        return listOfTeacherIds.map { instituteService.addTeacherToBatch(instituteId,batchId,it).toApi() }
    }

    @DeleteMapping(REMOVE_TEACHER_FROM_INSTITUTE)
    @InstituteOnly
    suspend fun removeTeacherFromInstitute(
        @PathVariable instituteId: String,
        @PathVariable teacherId: String
    ): TeacherDetails {
        return instituteService.removeTeacherFromInstitute(instituteId, teacherId).toApi()
    }

    @PostMapping(CREATE_BATCH)
    @InstituteOnly
    suspend fun createBatch(
        @PathVariable instituteId: String,
        @Valid  @RequestBody batchDetails: BatchDetails
    ): BatchDetails {
        return instituteService.createBatch(instituteId, batchDetails).toApi()
    }

    @PostMapping(ADD_STUDENT_TO_BATCH)
    @InstituteOnly
    suspend fun addStudentToBatch(
        @PathVariable instituteId: String,
        @PathVariable studentId: String,
        @PathVariable batchId: String
    ): StudentDetails {
        return instituteService.addStudentToBatch(instituteId, batchId, studentId).toApi()
    }

    @PostMapping(ADD_LIST_OF_STUDENTS_TO_BATCH)
    @InstituteOnly
    suspend fun addListOfStudentsToBatch(@PathVariable instituteId: String,@PathVariable batchId: String,@RequestBody listOfStudentIds:List<String>):List<StudentDetails>{
        return listOfStudentIds.map { instituteService.addStudentToBatch(instituteId,batchId,it).toApi() }
    }

    @GetMapping(GET_INSTITUTE_OVERVIEW)
    suspend fun getInstituteOverview(@PathVariable instituteId: String): Overview {
        val institute = instituteService.getInstituteById(instituteId)
        return Overview(
            instituteId,
            institute.name,
            institute.logo,
            institute.students.size.toLong(),
            institute.teachers.size.toLong(),
            institute.courses.filter { it.isDeleted }.size.toLong(),
            instituteService.getPastSchedules(instituteId).size.toLong(),
            instituteService.getLiveSchedules(instituteId).size.toLong(),
            instituteService.getUpcomingSchedules(instituteId).size.toLong(),
            institute.schedules.size.toLong(),
            instituteService.getTotalHours(instituteId)
        )
    }

    @PostMapping(INVITE_STUDENT_TO_INSTITUTE)
    @InstituteOnly
    suspend fun sendInviteToStudent(
        @PathVariable instituteId: String,
        @PathVariable studentId: String
    ): StudentDetails {
        return instituteService.sendInviteToStudent(instituteId, studentId).toApi()
    }

    @PostMapping(INVITE_STUDENT_TO_INSTITUTE_V2)
    @InstituteOnly
    suspend fun sendInviteToStudentV2(
        @PathVariable instituteId: String,
        @Valid  @RequestBody student: StudentDetails
    ): StudentDetails {
        return instituteService.sendInviteToStudent(instituteId, student).toApi()
    }

    @PostMapping(INVITE_TEACHER_TO_INSTITUTE)
    @InstituteOnly
    suspend fun sendInviteToTeacher(
        @PathVariable instituteId: String,
        @PathVariable teacherId: String
    ): TeacherDetails {
        return instituteService.sendInviteToTeacher(instituteId, teacherId).toApi()
    }

    @PostMapping(INVITE_TEACHER_TO_INSTITUTE_V2)
    @InstituteOnly
    suspend fun sendInviteToTeacherV2(
        @PathVariable instituteId: String,
        @Valid  @RequestBody teacherDetails: TeacherDetails
    ): TeacherDetails {
        return instituteService.sendInviteToTeacher(instituteId, teacherDetails).toApi()
    }

    @GetMapping(GET_STUDENT_INVITES)
    suspend fun getStudentInvites(@PathVariable instituteId: String): List<StudentInviteDetails> {
        return instituteService.getStudentInvites(instituteId).map { it.toApi() }
    }

    @GetMapping(GET_TEACHER_INVITES)
    suspend fun getTeacherInvites(@PathVariable instituteId: String): List<TeacherInviteDetails> {
        return instituteService.getTeacherInvites(instituteId).map { it.toApi() }
    }

    @PutMapping(UPDATE_INSTITUTE)
    @InstituteOnly
    suspend fun updateInstitute(
        @PathVariable instituteId: String,
        @Valid @RequestBody institute: InstituteDetails
    ): InstituteDetails {
        return instituteService.updateInstitute(instituteId, institute).toApi()
    }

    @GetMapping(GET_INSTITUTE_TEACHERS)
    suspend fun getInstituteTeachers(@PathVariable instituteId: String): List<TeacherDetails> {
        return instituteService.getInstituteTeachers(instituteId).map { it.toApi() }
    }

    @GetMapping(GET_INSTITUTE_STUDENTS)
    suspend fun getInstituteStudents(@PathVariable instituteId: String): List<StudentDetails> {
        return instituteService.getInstituteStudents(instituteId).map { it.toApi() }
    }

    @GetMapping(GET_INSTITUTE_COURSES)
    suspend fun getInstituteCourses(@PathVariable instituteId: String): List<CourseDetails> {
        return instituteService.getInstituteCourses(instituteId).map { it.toApi() }
    }

    @GetMapping(GET_INSTITUTE_BATCHES)
    suspend fun getInstituteBatches(@PathVariable instituteId: String): List<BatchDetails> {
        return instituteService.getInstituteBatches(instituteId).map { it.toApi() }
    }

    @GetMapping(GET_BATCH_BY_ID)
    suspend fun getBatchById(@PathVariable instituteId: String, @PathVariable batchId: String): BatchDetails {
        return instituteService.getBatchById(instituteId, batchId).toApi()
    }

    @GetMapping(GET_COURSE_BY_ID)
    suspend fun getCourseById(@PathVariable instituteId: String, @PathVariable courseId: String): CourseDetails {
        return instituteService.getCourseById(instituteId, courseId).toApi()
    }

    @PutMapping(UPDATE_COURSE)
    @InstituteOnly
    suspend fun updateCourse(
        @PathVariable instituteId: String,
        @PathVariable courseId: String,
        @Valid @RequestBody courseRequest: CourseDetails
    ): CourseDetails {
        return instituteService.updateCourse(instituteId, courseId, courseRequest).toApi()
    }

    @PutMapping(UPDATE_COURSE_SUBJECTS)
    @InstituteOnly
    suspend fun updateCourseSubjects(
        @PathVariable instituteId: String,
        @PathVariable courseId: String,
        @Valid @RequestBody courseRequest: CourseDetails
    ): CourseDetails {
        return instituteService.updateSubjects(instituteId, courseId, courseRequest).toApi()
    }


    @GetMapping(INSTITUTE_GET_ANNOUNCEMENTS)
    suspend fun getInstituteAnnouncements(@PathVariable instituteId: String): List<NoticeDetails> {
        return instituteService.getInstituteAnnouncements(instituteId).map { it.toApi() }
    }

    @PostMapping(CREATE_ANNOUNCEMENT)
    @TeacherAndInstituteOnly
    suspend fun createAnnouncement(
        @PathVariable instituteId: String,
        @Valid @RequestBody announcementDetails: AnnouncementDetails
    ): NoticeDetails {
        return instituteService.createAnnouncement(instituteId, announcementDetails).toApi()
    }

    @GetMapping(GET_ANNOUNCEMENT_BY_ID)
    suspend fun getAnnouncementById(@PathVariable instituteId: String, @PathVariable noticeId: String): NoticeDetails {
        return instituteService.getAnnouncementById(instituteId, noticeId).toApi()
    }

    @GetMapping(GET_COURSE_STUDY_MATERIAL)
    suspend fun getCourseStudyMaterial(
        @PathVariable instituteId: String,
        @PathVariable courseId: String
    ): List<StudyMaterialDetails> {
        return instituteService.getCourseStudyMaterial(instituteId, courseId).map { it.toApi() }
    }

    @PostMapping(CREATE_COURSE_STUDY_MATERIAL)
    @TeacherAndInstituteOnly
    suspend fun createCourseStudyMaterial(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @Valid @RequestBody studyMaterialDetails: StudyMaterialDetails
    ): StudyMaterialDetails {
        return instituteService.createCourseStudyMaterial(instituteId, batchId, studyMaterialDetails).toApi()
    }

    @GetMapping(GET_SCHEDULE_BY_ID)
    suspend fun getScheduleById(@PathVariable scheduleId: String): ScheduleDetails {
        return instituteService.getScheduleById(scheduleId).toApi()
    }

    @GetMapping(INSTITUTE_GET_SCHEDULES)
    suspend fun getInstituteSchedules(@PathVariable instituteId: String): List<ScheduleDetails> {
        return instituteService.getInstituteSchedules(instituteId).map { it.toApi() }
    }

    @GetMapping(GET_SCHEDULES)
    suspend fun getAllSchedules(): List<ScheduleDetails> {
        return instituteService.getAllSchedules().map { it.toApi() }
    }

    @PostMapping(ADD_TEACHER_TO_BATCH)
    @InstituteOnly
    suspend fun addTeacherToBatch(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable teacherId: String
    ): TeacherDetails {
        return instituteService.addTeacherToBatch(instituteId, batchId, teacherId).toApi()
    }

    @DeleteMapping(REMOVE_TEACHER_FROM_BATCH)
    @InstituteOnly
    suspend fun removeTeacherFromBatch(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable teacherId: String
    ): TeacherDetails {
        return instituteService.removeTeacherFromBatch(instituteId, batchId, teacherId).toApi()
    }

    @DeleteMapping(DELETE_ALL_INSTITUTE_ATTACHMENTS)
    @InstituteOnly
    suspend fun deleteAllInstituteAttachments(
        @PathVariable instituteId: String
    ): InstituteDetails {
        return instituteService.deleteAllInstituteAttachments(instituteId).toApi()
    }

    @DeleteMapping(DELETE_ALL_INSTITUTE_ANNOUNCEMENTS)
    @InstituteOnly
    suspend fun deleteAllInstituteAnnouncements(
        @PathVariable instituteId: String
    ): InstituteDetails {
        return instituteService.deleteAllInstituteAnnouncements(instituteId).toApi()
    }

    @GetMapping(GET_ALL_BATCH_STUDENTS)
    suspend fun getAllBatchStudents(@PathVariable instituteId: String,
                                    @PathVariable batchId: String,): List<StudentDetails> {
        return instituteService.getAllBatchStudents(instituteId, batchId).map { it.toApi() }
    }

    @GetMapping(GET_ALL_BATCH_TEACHERS)
    suspend fun getAllBatchTeachers(@PathVariable instituteId: String,
                                    @PathVariable batchId: String,): List<TeacherDetails> {
        return instituteService.getAllBatchTeachers(instituteId, batchId).map { it.toApi() }
    }

    @GetMapping(GET_BATCH_STUDY_MATERIALS)
    suspend fun getBatchStudyMaterials(@PathVariable instituteId: String,
                                    @PathVariable batchId: String,): List<StudyMaterialDetails> {
        return instituteService.getBatchStudyMaterials(instituteId, batchId).map { it.toApi() }
    }

    @DeleteMapping(REMOVE_STUDENT_FROM_BATCH)
    @InstituteOnly
    suspend fun removeStudentFromBatch(
        @PathVariable instituteId: String,
        @PathVariable batchId: String,
        @PathVariable studentId: String
    ): StudentDetails {
        return instituteService.removeStudentFromBatch(instituteId, batchId, studentId).toApi()
    }

    @GetMapping(GET_STUDENT_BATCHES_OF_INSTITUTE)
    suspend fun getStudentBatches(@PathVariable instituteId: String,@PathVariable studentId: String): List<CourseAndBatchDetails > {
        return instituteService.getStudentBatches(instituteId,studentId).map { it.toCourseAndBatchApi() }
    }

    @GetMapping(GET_ZOOM_HOST_URL_BY_MEETING_ID)
    suspend fun getZoomUrlById(@PathVariable meetingId: String): String {
        return instituteService.getZoomUrlById(meetingId.toLong())
    }

    @PostMapping(ADD_TOPIC_DETAILS)
    suspend fun addTopicDetails(@PathVariable instituteId: String,
                                @PathVariable courseId: String,
                                @PathVariable topicId: String,
                                @RequestBody topicAddDetails: TopicAddDetails): TopicAddDetails {
        return instituteService.addTopicDetails(instituteId, courseId, topicId, topicAddDetails)
    }

    @GetMapping(GET_TOPIC_DETAILS)
    suspend fun getTopicDetails(@PathVariable instituteId: String,
                                @PathVariable courseId: String,
                                @PathVariable topicId: String): TopicAddDetails {
        return instituteService.getTopicDetails(instituteId, courseId, topicId,)
    }

    @PostMapping(CREATE_COURSE_V2)
    @InstituteOnly
    suspend fun createCourseV2(
        @PathVariable instituteId: String,
        @Valid  @RequestBody courseDetails: CourseDetails
    ): CourseDetails {
        return instituteService.createCourseV2(instituteId, courseDetails).toApi()
    }

    @GetMapping(GET_COURSE_BY_ID_V2)
    suspend fun getCourseByIdV2(@PathVariable instituteId: String, @PathVariable courseId: String): CourseDetails {
        return instituteService.getCourseById(instituteId, courseId).toApi()
    }

    @GetMapping(GET_COURSE_BY_NAME)
    suspend fun searchBYCourseName(@RequestParam("search") searchKeyword: String): List<CourseDetails> {
        return instituteService.searchCourse(searchKeyword).map { it.toApi() }
    }

    @GetMapping(GET_INSTITUTE_AND_ROLE_BY_ID)
    suspend fun getUserInstituteAndRole(@RequestParam userId: String): Set<UserRoleDetails> {
        return instituteService.getUserInstituteAndRole(userId)
    }

    @GetMapping(GET_INSTITUTE_BY_NAME)
    suspend fun searchByInstituteName(@RequestParam("search") searchKeyword: String): List<InstituteDetails> {
        return instituteService.searchInstitute(searchKeyword).map { it.toApi() }
    }
}
