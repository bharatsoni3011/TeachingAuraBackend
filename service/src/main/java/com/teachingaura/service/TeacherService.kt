package com.teachingaura.service

import com.teachingaura.api.InviteType
import com.teachingaura.api.TeacherDetails
import com.teachingaura.db.*
import com.teachingaura.db.model.*
import com.teachingaura.exception.TeacherDoesNotExistException
import com.teachingaura.exception.UserAlreadyExistsException
import com.teachingaura.toApi
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class TeacherService @Inject constructor(
    val teacherRepository: TeacherRepository,
    private val testRepository: TestRepository,
    private val announcementsRepository: AnnouncementsRepository,
    private val scheduleRepository: ScheduleRepository,
    private val teacherInvitesRepository: TeacherInvitesRepository,
    @Lazy private val instituteService: InstituteService
) {

    suspend fun getTeacherById(id: String): Teacher {
        return teacherRepository.findByPid(id) ?: throw TeacherDoesNotExistException()
    }

    suspend fun getTeacherByPhoneNumber(phoneNumber: String): Teacher {
        return teacherRepository.findByContactNumber(phoneNumber) ?: throw TeacherDoesNotExistException()
    }

    suspend fun addTeacherToInstitute(studentId: String, institute: Institute): Teacher {
        val teacher = getTeacherById(studentId)
        teacher.addInstitute(institute)
        teacherRepository.save(teacher)
        return teacher
    }

    suspend fun removeTeacherFromInstitute(studentId: String, batches:List<Batch>, institute: Institute): Teacher {
        val teacher = getTeacherById(studentId)
        batches.forEach { teacher.removeBatch(it) }
        teacherInvitesRepository.deleteById_InstituteIdAndId_TeacherId(institute.pid, studentId)
        teacher.removeInstitute(institute)
        teacherRepository.save(teacher)
        return teacher
    }


    suspend fun createTeacher(teacherDetails: TeacherDetails): Teacher {
        if (teacherRepository.existsById(teacherDetails.id)) {
            throw UserAlreadyExistsException("User Already exists!")
        }
        val teacher = Teacher(
            teacherDetails.id, teacherDetails.name, teacherDetails.contactNumber,
            teacherDetails.email, teacherDetails.subject
        )
        return teacherRepository.save(teacher)
    }

    suspend fun getAllTeachers(): List<TeacherDetails> {
        return teacherRepository.findAll().map { it.toApi() }
    }

    suspend fun updateTeacher(teacherId: String, teacherDetails: TeacherDetails): Teacher {
        val teacher = getTeacherById(teacherId)
        teacher.name = teacherDetails.name
        teacher.email = teacherDetails.email
        teacher.subject = teacherDetails.subject
        return teacherRepository.save(teacher)
    }

    suspend fun addTeacherToBatch(teacherId: String, institute: Institute, batch: Batch): Teacher {
        val teacher = getTeacherById(teacherId)
        teacher.addBatch(batch)
        teacherRepository.save(teacher)
        return teacher
    }

    suspend fun removeTeacherFromBatch(teacherId: String, institute: Institute, batch: Batch): Teacher {
        val teacher = getTeacherById(teacherId)
        teacher.removeBatch(batch)
        teacherRepository.save(teacher)
        return teacher
    }

    suspend fun getTeacherBatches(teacherId: String): List<Batch> {
        val teacher = getTeacherById(teacherId)
        return teacher.batches.toList()
    }

    suspend fun getTeacherTests(teacherId: String): List<Test> {
        val batches = getTeacherBatches(teacherId)
        return testRepository.findByBatchPid(batches)
    }

    suspend fun getInstitutesForTeacher(teacherId: String): List<Institute> {
        val teacher = getTeacherById(teacherId)
        return teacher.institutes.toList()
    }

    suspend fun getTeacherAnnouncements(teacherId: String): List<Announcement> {
        val institutesForTeacher = getInstitutesForTeacher(teacherId)
        return announcementsRepository.findByInstituteIn(institutesForTeacher)
    }

    suspend fun getTeacherSchedules(teacherId: String): List<Schedule> {
        var batches = getTeacherBatches(teacherId)
        return scheduleRepository.findAllByBatchIn(batches)
    }

    suspend fun getTeacherCourses(teacherId: String): List<Course> {
        val teacher = getTeacherById(teacherId)
        return teacher.batches.map { it.course }
    }

    suspend fun getTeacherInstitutes(teacherId: String): List<Institute> {
        val teacher = getTeacherById(teacherId)
        return teacher.institutes
    }

    fun getTeacherInvites(teacherId: String): List<TeacherInvites> {
        return teacherInvitesRepository.findAllById_TeacherId(teacherId)
    }

    suspend fun updateInstituteInvite(teacherId: String, instituteId: String, status: InviteType): TeacherInvites {
        val teacherInvite = teacherInvitesRepository.findById_InstituteIdAndId_TeacherId(instituteId, teacherId)
        val updatedStudentInvite = teacherInvite.updateStatus(status)
        if (status == InviteType.ACCEPT) {
            instituteService.addTeacherToInstitute(instituteId, teacherId)
        }
        return teacherInvitesRepository.save(updatedStudentInvite)
    }

    suspend fun getTeacherSchedules(teacherId: String, instituteId: String): List<Schedule> {
        val teacher = getTeacherById(teacherId)
        val batches = teacher.batches.filter { it.institute.pid  == instituteId }
        return scheduleRepository.findAllByBatchIn(batches)
    }
}
