package com.teachingaura.controller

import com.teachingaura.api.*
import com.teachingaura.api.API.CREATE_STUDENT
import com.teachingaura.api.API.DELETE_STUDENT
import com.teachingaura.api.API.GET_BATCHES
import com.teachingaura.api.API.GET_INVITES_STUDENT
import com.teachingaura.api.API.GET_SCHEDULE
import com.teachingaura.api.API.GET_STUDENT
import com.teachingaura.api.API.GET_STUDENTS
import com.teachingaura.api.API.GET_STUDENT_ANNOUNCEMENTS
import com.teachingaura.api.API.GET_STUDENT_ENROLLED_COURSES
import com.teachingaura.api.API.GET_STUDENT_INSTITUTES
import com.teachingaura.api.API.STUDENT_ACCEPT_INSTITUTE_INVITE
import com.teachingaura.api.API.STUDENT_REJECT_INSTITUTE_INVITE
import com.teachingaura.api.API.UPDATE_STUDENT
import com.teachingaura.roles.Role
import com.teachingaura.roles.RoleService
import com.teachingaura.roles.StudentOnly
import com.teachingaura.service.StudentService
import com.teachingaura.toApi
import com.teachingaura.toCourseAndBatchApi
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class StudentController @Inject constructor(
    private val studentService: StudentService,
    private val roleService: RoleService
) : StudentApi, BaseController() {

    @GetMapping(GET_STUDENTS)
    override suspend fun getStudents(): List<StudentDetails> {
        return studentService
            .getAllStudents()
            .map { it.toApi() }
            .toList()
    }

    @GetMapping(GET_STUDENT)
    override suspend fun getById(@PathVariable studentId: String): StudentDetails {
        if (studentId == "") {
            throw Exception("Invalid Id present in the request!!")
        }
        return studentService.getStudent(studentId).toApi()
    }

    @PostMapping(CREATE_STUDENT)
    override suspend fun createStudent(@Valid  @RequestBody request: StudentDetails): StudentDetails {
        //val userUid = UserContext.getUid()
        val newStudent = studentService.createStudent(request)
        roleService.addRoleToUser(request.id, Role.ROLE_STUDENT)
        return newStudent.toApi()
    }

    @GetMapping(GET_INVITES_STUDENT)
    override suspend fun getStudentInvites(@PathVariable studentId: String): List<StudentInviteDetails> {
        return studentService.getStudentInvites(studentId).map {
            it.toApi()
        }
    }

    @PostMapping(STUDENT_ACCEPT_INSTITUTE_INVITE)
    @StudentOnly
    override suspend fun acceptInstituteInvite(@PathVariable studentId: String, @PathVariable instituteId: String):
            StudentInviteDetails {
        return studentService.updateInstituteInvite(studentId, instituteId, InviteType.ACCEPT).toApi()
    }

    @PostMapping(STUDENT_REJECT_INSTITUTE_INVITE)
    @StudentOnly
    override suspend fun rejectInstituteInvite(@PathVariable studentId: String, @PathVariable instituteId: String):
            StudentInviteDetails {
        return studentService.updateInstituteInvite(studentId, instituteId, InviteType.REJECT).toApi()
    }

    @GetMapping(GET_BATCHES)
    override suspend fun getStudentEnrolledBatches(@PathVariable studentId: String): List<BatchDetails> {
        return studentService
            .getStudentBatches(studentId)
            .map { it.toApi() }
    }

    @GetMapping(GET_STUDENT_ANNOUNCEMENTS)
    override suspend fun getStudentAnnouncements(@PathVariable studentId: String): List<NoticeDetails> {
        return studentService
            .getStudentAnnouncements(studentId)
            .map { it.toApi() }
    }

    @GetMapping(GET_SCHEDULE)
    override suspend fun getStudentScheduleForTimeRange(
        @PathVariable studentId: String,
        @RequestParam startTime: Long,
        @RequestParam endTime: Long
    ): List<ScheduleDetails> {
        return studentService
            .getScheduleForTimeRange(studentId, startTime, endTime)
            .map { it.toApi() }
            .toList()
    }

    @GetMapping(GET_STUDENT_INSTITUTES)
    override suspend fun getStudentInstitutes(@PathVariable studentId: String): List<InstituteDetails> {
        return studentService
            .getInstitutesForStudent(studentId)
            .map { it.toApi() }
    }

    @DeleteMapping(DELETE_STUDENT)
    override suspend fun deleteStudent(@PathVariable studentId: String) {
        studentService.deleteStudent(studentId)
    }

    @GetMapping(GET_STUDENT_ENROLLED_COURSES)
    override suspend fun getStudentEnrolledCourses(
        @PathVariable studentId: String
    ): List<CourseAndBatchDetails> {
        return studentService.getStudentEnrolledBatches(studentId).map { it.toCourseAndBatchApi() }
    }

    @PutMapping(UPDATE_STUDENT)
    @StudentOnly
    override suspend fun updateStudent(
        @PathVariable studentId: String,
        @Valid @RequestBody studentDetails: StudentDetails
    ): StudentDetails {
        return studentService.updateStudent(studentId, studentDetails).toApi()
    }

    @GetMapping(API.GET_STUDENT_TESTS)
    suspend fun getStudentTests(
        @PathVariable studentId: String
    ): List<TestDetails> {
        return studentService.getStudentTests(studentId).map { it.toApi() }
    }

    @GetMapping(API.GET_STUDENT_SCHEDULES)
    suspend fun getStudentSchedules(
        @PathVariable studentId: String,
        @PathVariable instituteId: String
    ): List<ScheduleDetails> {
        return studentService.getStudentSchedules(studentId, instituteId).map { it.toApi() }
    }
}
