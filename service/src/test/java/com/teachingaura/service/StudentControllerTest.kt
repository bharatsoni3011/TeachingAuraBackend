package com.teachingaura.service

import com.teachingaura.IntegrationBaseTest
import com.teachingaura.api.MaxEducation
import com.teachingaura.api.StudentDetails
import com.teachingaura.contexts.FirebaseUserInfo
import com.teachingaura.contexts.UserContext
import com.teachingaura.controller.InstituteController
import com.teachingaura.controller.StudentController
import com.teachingaura.db.InstituteRepository
import com.teachingaura.db.StudentRepository
import com.teachingaura.db.model.Institute
import com.teachingaura.db.model.Student
import com.teachingaura.exception.UserAlreadyExistsException
import com.teachingaura.exception.UserDoesNotExistException
import com.teachingaura.roles.Role
import com.teachingaura.toApi
import com.teachingaura.util.InstituteUtil
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser
import java.time.LocalDateTime

class StudentControllerTest : IntegrationBaseTest() {

    @Autowired
    private lateinit var studentController: StudentController

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var instituteController: InstituteController

    @Autowired
    private lateinit var instituteRepository: InstituteRepository

    @Autowired
    private lateinit var instituteUtil: InstituteUtil

    private lateinit var testStudent: Student
    private lateinit var testStudentDetails: StudentDetails
    private lateinit var testInstitute: Institute

    @BeforeEach
    fun init() {
        testStudent = Student(
            "test_id",
            "Test Name",
            "test@gmail.com",
            "1234567",
            TEST_DATE_OF_BIRTH,
            MaxEducation.BELOW_MATRICULATE
        )
        studentRepository.save(testStudent)
    }

    @Test
    fun getStudentDetails_shouldReturnCorrectStudentInfo_test() = runBlocking {
        val (id, name, email, contactNumber, maxEducation, dateOfBirth) = studentController.getById("test_id")
        Assertions.assertEquals(testStudent.contactNumber, contactNumber)
        Assertions.assertEquals(testStudent.email, email)
        Assertions.assertEquals(testStudent.name, name)
        Assertions.assertEquals(testStudent.pid, id)
        Assertions.assertEquals(testStudent.dateOfBirth, dateOfBirth)
        Assertions.assertEquals(testStudent.maxEducation, maxEducation)

    }

    @Test
    fun getAllStudents_shouldReturnAllStudentsInfo_test() = runBlocking {
        Assertions.assertEquals(
            listOf(testStudent.toApi()),
            studentController.getStudents()
        )
    }

    @Test
    fun getStudentDetails_shouldThrowUserDoesNotExistException_whenStudentDoesNotExist_test() {
        studentRepository.delete(testStudent)
        Assertions.assertThrows(UserDoesNotExistException::class.java) { runBlocking { studentController.getById("test_id") } }
    }

    @Test
    fun createStudent_shouldCreateStudentWithCorrectStudentInfo_test() = runBlocking {
        UserContext.setInfo(FirebaseUserInfo("test_id"))
        studentRepository.delete(testStudent)
        studentController.createStudent(
            StudentDetails(
                TEST_ID, TEST_NAME, TEST_GMAIL_COM, TEST_CONTACT_NUMBER, MaxEducation.BELOW_MATRICULATE,
                TEST_DATE_OF_BIRTH
            )
        )
        val (id, name, email, contactNumber, maxEducation, dateOfBirth) = studentController.getById("test_id")
        Assertions.assertEquals(testStudent.contactNumber, contactNumber)
        Assertions.assertEquals(testStudent.email, email)
        Assertions.assertEquals(testStudent.name, name)
        Assertions.assertEquals(testStudent.pid, id)
        Assertions.assertEquals(testStudent.dateOfBirth, dateOfBirth)
        Assertions.assertEquals(testStudent.maxEducation, maxEducation)
    }

    @Test
    fun createStudent_shouldThrowUserExistsException_whenUserIsAlreadyPresent_test() {
        UserContext.setInfo(FirebaseUserInfo("test_id"))
        Assertions.assertThrows(UserAlreadyExistsException::class.java) {
            runBlocking {
                studentController.createStudent(
                    StudentDetails(
                        TEST_ID,
                        TEST_NAME,
                        TEST_CONTACT_NUMBER,
                        TEST_GMAIL_COM,
                        MaxEducation.BELOW_MATRICULATE,
                        TEST_DATE_OF_BIRTH
                    )
                )
            }
        }
    }

    @Test
    fun getSchedule_shouldReturnCorrectScheduleDetails_test() {
    }

    @Test
    fun deleteStudent_shouldDeleteStudentInfoFromDatabase_test() = runBlocking {
        val (id, name, email, contactNumber, maxEducation, dateOfBirth) = studentController.getById("test_id")
        Assertions.assertEquals(testStudent.contactNumber, contactNumber)
        Assertions.assertEquals(testStudent.email, email)
        Assertions.assertEquals(testStudent.name, name)
        Assertions.assertEquals(testStudent.pid, id)
        Assertions.assertEquals(testStudent.dateOfBirth, dateOfBirth)
        Assertions.assertEquals(testStudent.maxEducation, maxEducation)
        studentController.deleteStudent("test_id")
        Assertions.assertThrows(UserDoesNotExistException::class.java) { runBlocking { studentController.getById("test_id") } }
    }


    @Test
    fun deleteStudent_shouldDeleteStudent_test() {
        Assertions.assertThrows(UserDoesNotExistException::class.java) {
            runBlocking {
                studentController.deleteStudent(testStudent.pid)
                studentController.getById("test_id")
            }
        }
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["STUDENT"])
    fun updateStudent_shouldUpdateStudent_test() = runBlocking {

        testStudentDetails = StudentDetails(
            TEST_ID, "UPDATE NAME", TEST_GMAIL_COM, TEST_CONTACT_NUMBER, MaxEducation.BELOW_MATRICULATE,
            TEST_DATE_OF_BIRTH,
            "https://storage.googleapis.com/teachingaura.appspot.com/test_id/student/profile"
        )
        val existingStudent = studentService.getStudent(TEST_ID)
        studentController.updateStudent(
            testStudent.pid,
            testStudentDetails
        )
        val updatedStudent = studentService.getStudent(TEST_ID)
        Assertions.assertTrue(updatedStudent.createdAt == existingStudent.createdAt)
        Assertions.assertTrue(updatedStudent.modifiedAt > existingStudent.modifiedAt)
        Assertions.assertEquals(testStudentDetails, studentController.getById(testStudent.pid))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["SUPER"])
    fun getStudentInstitutes_shouldGetStudentInstitutes_test() = runBlocking {
        testInstitute = Institute(
            TEST_ID,
            TEST_NAME,
            InstituteControllerTest.TEST_LOGO,
            TEST_GMAIL_COM,
            TEST_CONTACT_NUMBER,
            ABOUT_US,
            TEST_NAME,
            TEST_GMAIL_COM
        )
        instituteController.createInstitute(testInstitute.toApi())
        instituteController.addStudentToInstitute(testInstitute.pid, testStudent.pid)
        Assertions.assertEquals(listOf(testInstitute.toApi()), studentController.getStudentInstitutes(testStudent.pid))
    }

    @Test
    @WithMockUser(username = InstituteControllerTest.TEST_ID, roles = ["SUPER"])
    fun getUserInstituteAndRole_test() = runBlocking {
        testInstitute = instituteRepository.save(instituteUtil.getInstitute())
        instituteController.addStudentToInstitute(testInstitute.pid, testStudent.pid)
        Assertions.assertEquals(setOf(instituteUtil.getUserRoleDetails_Student()),instituteController.getUserInstituteAndRole(
            TeacherControllerTest.TEST_ID
        ))
    }

    @AfterEach
    fun cleanup() {
        studentRepository.deleteAll()
        instituteRepository.deleteAll()
    }

    companion object {
        val TEST_DATE_OF_BIRTH = LocalDateTime.now().toLocalDate().toEpochDay()
        const val TEST_ID = "test_id"
        const val TEST_NAME = "Test Name"
        const val TEST_GMAIL_COM = "test@gmail.com"
        const val TEST_CONTACT_NUMBER = "1234567"
        const val ABOUT_US = "aboutUs"
    }
}
