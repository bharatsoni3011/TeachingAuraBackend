package com.teachingaura.controller

import com.teachingaura.api.*
import com.teachingaura.api.API.CREATE_TEACHER
import com.teachingaura.api.API.GET_TEACHER_ANNOUNCEMENTS
import com.teachingaura.api.API.GET_TEACHER_BATCHES
import com.teachingaura.api.API.GET_TEACHER_BY_ID
import com.teachingaura.api.API.GET_TEACHER_COURSES
import com.teachingaura.api.API.GET_TEACHER_INSTITUTES
import com.teachingaura.api.API.GET_TEACHER_SCHEDULE
import com.teachingaura.api.API.GET_TEACHER_TESTS
import com.teachingaura.api.API.TEACHERS
import com.teachingaura.api.API.UPDATE_TEACHER
import com.teachingaura.roles.Role
import com.teachingaura.roles.RoleService
import com.teachingaura.service.TeacherService
import com.teachingaura.toApi
import com.teachingaura.toCourseAndBatchApi
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class TeacherController @Inject constructor(private val teacherService: TeacherService,
                                            private val roleService: RoleService
) : BaseController() {

    @PostMapping(CREATE_TEACHER)
    suspend fun createTeacher(@Valid @RequestBody teacherDetails: TeacherDetails): TeacherDetails {
        val teacher = teacherService.createTeacher(teacherDetails).toApi()
        roleService.addRoleToUser(teacherDetails.id, Role.ROLE_TEACHER)
        return teacher
    }

    @GetMapping(GET_TEACHER_BY_ID)
    suspend fun getTeacher(@PathVariable teacherId: String): TeacherDetails {
        return teacherService.getTeacherById(teacherId).toApi()
    }

    @GetMapping(TEACHERS)
    suspend fun getAllTeachers(): List<TeacherDetails> {
        return teacherService.getAllTeachers()
    }

    @PutMapping(UPDATE_TEACHER)
    suspend fun updateTeacher(
        @PathVariable teacherId: String,
        @Valid @RequestBody teacherDetails: TeacherDetails
    ): TeacherDetails {
        return teacherService.updateTeacher(teacherId, teacherDetails).toApi()
    }

    @GetMapping(GET_TEACHER_TESTS)
    suspend fun getTeacherTests(
        @PathVariable teacherId: String
    ): List<TestDetails> {
        return teacherService.getTeacherTests(teacherId).map { it.toApi() }
    }

    @GetMapping(GET_TEACHER_ANNOUNCEMENTS)
    suspend fun getTeacherAnnouncements(@PathVariable teacherId: String): List<NoticeDetails> {
        return teacherService
            .getTeacherAnnouncements(teacherId)
            .map { it.toApi() }
    }

    @GetMapping(GET_TEACHER_BATCHES)
    suspend fun getTeacherBatches(@PathVariable teacherId: String): List<BatchDetails> {
        return teacherService
            .getTeacherBatches(teacherId)
            .map { it.toApi() }
    }

    @GetMapping(GET_TEACHER_SCHEDULE)
    suspend fun getTeacherSchedules(@PathVariable teacherId: String): List<ScheduleDetails> {
        return teacherService
            .getTeacherSchedules(teacherId)
            .map { it.toApi() }
    }

    @GetMapping(GET_TEACHER_COURSES)
    suspend fun getTeacherCourses(@PathVariable teacherId: String): List<CourseAndBatchDetails> {
        return teacherService
            .getTeacherBatches(teacherId)
            .map { it.toCourseAndBatchApi() }
    }

    @GetMapping(GET_TEACHER_INSTITUTES)
    suspend fun getTeacherInstitutes(@PathVariable teacherId: String): List<InstituteDetails> {
        return teacherService
            .getTeacherInstitutes(teacherId)
            .map { it.toApi() }
    }

    @GetMapping(API.GET_INVITES_TEACHER)
    suspend fun getTeacherInvites(@PathVariable teacherId: String): List<TeacherInviteDetails> {
        return teacherService.getTeacherInvites(teacherId).map {
            it.toApi()
        }
    }

    @PostMapping(API.TEACHER_ACCEPT_INSTITUTE_INVITE)
    suspend fun acceptInstituteInvite(@PathVariable teacherId: String, @PathVariable instituteId: String):
            TeacherInviteDetails {
        return teacherService.updateInstituteInvite(teacherId, instituteId, InviteType.ACCEPT).toApi()
    }

    @PostMapping(API.TEACHER_REJECT_INSTITUTE_INVITE)
    suspend fun rejectInstituteInvite(@PathVariable teacherId: String, @PathVariable instituteId: String):
            TeacherInviteDetails {
        return teacherService.updateInstituteInvite(teacherId, instituteId, InviteType.REJECT).toApi()
    }

    @GetMapping(API.GET_TEACHER_SCHEDULES)
    suspend fun getTeacherSchedules(@PathVariable teacherId: String, @PathVariable instituteId: String): List<ScheduleDetails> {
        return teacherService.getTeacherSchedules(teacherId, instituteId).map { it.toApi() }
    }
}
