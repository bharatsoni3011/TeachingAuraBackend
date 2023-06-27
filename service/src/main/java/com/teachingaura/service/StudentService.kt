package com.teachingaura.service

import com.teachingaura.api.InviteType
import com.teachingaura.api.StudentDetails
import com.teachingaura.db.*
import com.teachingaura.db.model.*
import com.teachingaura.exception.UserAlreadyExistsException
import com.teachingaura.exception.UserDoesNotExistException
import mu.KotlinLogging
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.inject.Inject

private val logger = KotlinLogging.logger {}

@Service
class StudentService @Inject constructor(
    private val studentRepository: StudentRepository,
    private val scheduleRepository: ScheduleRepository,
    private val testRepository: TestRepository,
    private val announcementsRepository: AnnouncementsRepository,
    private val studentInvitesRepository: StudentInvitesRepository,
    @Lazy private val instituteService: InstituteService
) {


    fun getAllStudents(): List<Student> {
        return getAllStudentsIfExists()
    }

    fun getStudent(id: String): Student {
        return getStudentIfExists(id)
    }

    fun getStudentByPhoneNumber(phoneNumber: String): Student {
        return studentRepository.findByContactNumber(phoneNumber) ?: throw UserDoesNotExistException()
    }

    fun createStudent(request: StudentDetails): Student {
        if (studentRepository.existsById(request.id)) {
            throw UserAlreadyExistsException("User Already exists!")
        }
        val newStudent = Student(
            id = request.id,
            name = request.name,
            email = request.email,
            contactNumber = request.contactNumber,
            dateOfBirth = request.dateOfBirth,
            maxEducation = request.maxEducation
        )
        return studentRepository.save(newStudent)
    }

    fun getStudentBatches(id: String): List<Batch> {
        // TODO: avoid 2 different calls: one for student and other for getting batches list
        val student = getStudentIfExists(id)
        return student.batches.toList()
    }

    fun getScheduleForTimeRange(studentId: String, startTime: Long, endTime: Long): List<Schedule> {
        // TODO: use joins here
        logger.info { "GetScheduleRequest : Start time : $startTime End time : $endTime" }
        val batches = getStudentBatches(studentId).map { it.pid }
        logger.info { "Student batches are : $batches for student : $studentId" }
        return scheduleRepository.findAllByBatchInAndStartTimeIsBetween(
            getStudent(studentId).batches.toList(),
            startTime,
            endTime
        )
    }

    fun getInstitutesForStudent(studentId: String): List<Institute> {
        val student = getStudentIfExists(studentId)
        return student.institutes.toList()
    }
/*
    fun getStudentEnrolledCourses(studentId: String, institutes: List<String>): List<Course> {
        val studentBatches = getStudentBatches(studentId)
        return studentBatches
            .map { obj: Batch -> obj.course }
            .filter { course: Course ->
                (institutes.isEmpty()
                        || institutes.contains(course.institute.pid))
            }
    }
*/

    fun getStudentEnrolledBatches(studentId: String): List<Batch> {
        return getStudentBatches(studentId)
    }

    private fun getStudentIfExists(id: String): Student {
        return studentRepository.findByPid(id) ?: throw UserDoesNotExistException()
    }


    private fun getAllStudentsIfExists(): List<Student> {
        return studentRepository.findAll().toList()
    }

    fun getStudentAnnouncements(studentId: String): List<Announcement> {
        val institutesForStudent = getInstitutesForStudent(studentId)
        return announcementsRepository.findByInstituteIn(institutesForStudent)
    }

    fun deleteStudent(studentId: String) {
        val student = getStudent(studentId)
        student.removeAllInvites()
        student.removeAllBatches()
        student.removeAllInstitutes()
        studentRepository.deleteById(studentId)
    }

    fun addStudentToInstitute(studentId: String, institute: Institute): Student {
        val student = getStudent(studentId)
        student.addInstitute(institute)
        studentRepository.save(student)
        return student
    }

    @Transactional
    fun removeStudentFromInstitute(studentId: String, institute: Institute): Student {
        val student = getStudent(studentId)
        student.removeInstitute(institute)
        studentInvitesRepository.deleteById_InstituteIdAndId_StudentId(institute.pid, studentId)
        studentRepository.save(student)
        return student
    }

    fun addStudentToBatch(studentID: String, institute: Institute, batch: Batch): Student {
        val student = getStudent(studentID)
        student.addBatch(batch)
        return studentRepository.save(student)
    }

    fun updateStudent(studentId: String, studentDetails: StudentDetails): Student {
        val student = getStudentIfExists(studentId).apply {
            name = studentDetails.name
            email = studentDetails.email
            dateOfBirth = studentDetails.dateOfBirth
            maxEducation = studentDetails.maxEducation
        }
        return studentRepository.save(student)
    }

    suspend fun getStudentTests(studentId: String): List<Test> {
        val batches = getStudentBatches(studentId)
        return testRepository.findByBatchPid(batches)
    }

    suspend fun removeStudentFromBatch(studentId: String, institute: Institute, batch: Batch): Student {
        val student = getStudent(studentId)
        student.removeBatch(batch)
        studentRepository.save(student)
        return student
    }

    suspend fun getStudentInvites(studentId: String): List<StudentInvites> {
        return studentInvitesRepository.findAllById_StudentId(studentId)
    }

    suspend fun updateInstituteInvite(studentId: String, instituteId: String, status: InviteType): StudentInvites {
        val studentInvite = studentInvitesRepository.findById_InstituteIdAndId_StudentId(instituteId, studentId)
        val updatedStudentInvite = studentInvite.updateStatus(status)
        if (status == InviteType.ACCEPT) {
            instituteService.addStudentToInstitute(instituteId, studentId)
        }
        return studentInvitesRepository.save(updatedStudentInvite)
    }

    suspend fun getStudentSchedules(studentId: String, instituteId: String): List<Schedule> {
        val student = getStudent(studentId)
        val batches = student.batches.filter { it.institute.pid  == instituteId }
        return scheduleRepository.findAllByBatchIn(batches)
    }
}
