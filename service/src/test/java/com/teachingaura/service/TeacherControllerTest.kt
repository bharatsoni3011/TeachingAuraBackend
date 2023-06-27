package com.teachingaura.service

import com.teachingaura.IntegrationBaseTest
import com.teachingaura.api.TeacherDetails
import com.teachingaura.api.TestV2Details
import com.teachingaura.controller.InstituteController
import com.teachingaura.controller.TeacherController
import com.teachingaura.controller.TestController
import com.teachingaura.db.*
import com.teachingaura.db.model.Teacher
import com.teachingaura.exception.TeacherDoesNotExistException
import com.teachingaura.exception.UserAlreadyExistsException
import com.teachingaura.toApi
import com.teachingaura.util.InstituteUtil
import com.teachingaura.util.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser

class TeacherControllerTest : IntegrationBaseTest() {

    @Autowired
    private lateinit var teacherController: TeacherController

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    private lateinit var testTeacher: Teacher

    @Autowired
    private lateinit var testController: TestController

    @Autowired
    private lateinit var instituteController: InstituteController

    @Autowired
    private lateinit var testUtil: TestUtil

    @Autowired
    private lateinit var testRepository: TestRepository

    @Autowired
    private lateinit var instituteRepository: InstituteRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var batchRepository: BatchRepository

    @Autowired
    private lateinit var instituteUtil: InstituteUtil

    @Autowired
    private lateinit var announcementsRepository: AnnouncementsRepository

    @BeforeEach
    fun init() {
        testTeacher = Teacher(
            TEST_ID,
            TEST_NAME,
            TEST_CONTACT_NUMBER,
            TEST_GMAIL_COM,
            TEST_SUBJECT
        )
        teacherRepository.save(testTeacher)
    }

    @AfterEach
    fun cleanup() {
        announcementsRepository.deleteAll()
        testRepository.deleteAll()
        teacherRepository.deleteAll()
        batchRepository.deleteAll()
        courseRepository.deleteAll()
        instituteRepository.deleteAll()
    }

    companion object {
        const val TEST_ID = "test_id"
        const val TEST_NAME = "Test Name"
        const val TEST_CONTACT_NUMBER = "1234567"
        const val TEST_GMAIL_COM = "test@gmail.com"
        const val TEST_SUBJECT = "subject"
    }

    @Test
    fun createTeacher_shouldCreateTeacherWithCorrectTeacherInfo_test() = runBlocking {
        teacherRepository.delete(testTeacher)
        teacherController.createTeacher(
            TeacherDetails(
                TeacherControllerTest.TEST_ID,
                TeacherControllerTest.TEST_NAME,
                TeacherControllerTest.TEST_CONTACT_NUMBER,
                TeacherControllerTest.TEST_GMAIL_COM,
                TeacherControllerTest.TEST_SUBJECT
            )
        )
        val (id, name, contactNumber, email, subject) = teacherController.getTeacher(TEST_ID)
        Assertions.assertEquals(testTeacher.pid, id)
        Assertions.assertEquals(testTeacher.name, name)
        Assertions.assertEquals(testTeacher.contactNumber, contactNumber)
        Assertions.assertEquals(testTeacher.email, email)
        Assertions.assertEquals(testTeacher.subject, subject)
    }

    @Test
    fun createTeacher_shouldThrowUserExistsException_whenUserIsAlreadyPresent_test() {
        Assertions.assertThrows(UserAlreadyExistsException::class.java) {
            runBlocking {
                teacherController.createTeacher(
                    TeacherDetails(
                        TEST_ID,
                        TEST_NAME,
                        TEST_CONTACT_NUMBER,
                        TEST_GMAIL_COM,
                        TEST_SUBJECT
                    )
                )
            }
        }
    }

    @Test
    fun getTeacher_shouldReturnCorrectTeacherInfo_test() = runBlocking {
        val (id, name, email, contactNumber, subject) = teacherController.getTeacher(TEST_ID)
        Assertions.assertEquals(testTeacher.pid, id)
        Assertions.assertEquals(testTeacher.name, name)
        Assertions.assertEquals(testTeacher.contactNumber, contactNumber)
        Assertions.assertEquals(testTeacher.email, email)
        Assertions.assertEquals(testTeacher.subject, subject)
    }

    @Test
    fun getTeacher_shouldThrowTeacherDoesNotExistException_whenTeacherDoesNotExist_test() {
        teacherRepository.delete(testTeacher)
        Assertions.assertThrows(TeacherDoesNotExistException::class.java) {
            runBlocking {
                teacherController.getTeacher(
                    TEST_ID
                )
            }
        }
    }

    @Test
    fun getAllTeachers_shouldReturnAllTeachersInfo_test() = runBlocking {
        Assertions.assertEquals(
            listOf(testTeacher.toApi()),
            teacherController.getAllTeachers()
        )
    }

    @Test
    fun updateTeacher_shouldUpdateTeacherAndReturnUpdatedTeacherInfo_test() = runBlocking {
        val (id, name, email, contactNumber, subject) = teacherController.updateTeacher(
            TEST_ID,
            TeacherDetails(
                TEST_ID,
                TEST_NAME,
                TEST_GMAIL_COM,
                TEST_CONTACT_NUMBER,
                "TEST_SUBJECT"
            )
        )
        Assertions.assertEquals(testTeacher.pid, id)
        Assertions.assertEquals(testTeacher.name, name)
        Assertions.assertEquals(testTeacher.contactNumber, contactNumber)
        Assertions.assertEquals(testTeacher.email, email)
        Assertions.assertEquals("TEST_SUBJECT", subject)
    }

    @Test
    fun updateTeacher_shouldThrowTeacherDoesNotExistException_whenTeacherDoesNotExist_test() {
        teacherRepository.delete(testTeacher)
        Assertions.assertThrows(TeacherDoesNotExistException::class.java) {
            runBlocking {
                teacherController.updateTeacher(
                    TEST_ID,
                    TeacherDetails(
                        TEST_ID,
                        TEST_NAME,
                        TEST_CONTACT_NUMBER,
                        TEST_GMAIL_COM,
                        "TEST_SUBJECT"
                    )
                )
            }
        }
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["SUPER"])
    fun getTeacherTests_shouldGetTeacherTests_test() = runBlocking {
        instituteRepository.save(instituteUtil.getInstitute())
        courseRepository.save(instituteUtil.getCourse())
        batchRepository.save(instituteUtil.getBatch())
        testController.createTest(TestControllerTest.TEST_ID, TestControllerTest.TEST_ID, testUtil.getTestDetails())
        instituteController.addTeacherToBatch(
            TEST_ID,
            TEST_ID,
            TEST_ID
        )
        Assertions.assertEquals(listOf(testUtil.getTestDetails()), teacherController.getTeacherTests(TEST_ID))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["SUPER"])
    fun getTeacherAnnouncements_shouldGetTeacherAnnouncements_test() = runBlocking {
        var testAnnouncementDetails = instituteUtil.getAnnouncementDetailsWithoutAttachment()
        var testInstitute = instituteUtil.getInstitute()
        instituteRepository.save(testInstitute)
        instituteController.createAnnouncement(testInstitute.pid, testAnnouncementDetails)
        Assertions.assertEquals(
            listOf(instituteUtil.getNoticeDetailsWithoutAttachment()),
            instituteController.getInstituteAnnouncements(testInstitute.pid).map { instituteUtil.getNoticeDetailsWithoutAttachment(it) }.toList()
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["SUPER"])
    fun getTeacherBatches_shouldGetTeacherBatches_test() = runBlocking {
        instituteRepository.save(instituteUtil.getInstitute())
        courseRepository.save(instituteUtil.getCourse())
        batchRepository.save(instituteUtil.getBatch())
        instituteController.addTeacherToBatch(
            TEST_ID,
            TEST_ID,
            TEST_ID
        )
        Assertions.assertEquals(listOf(instituteUtil.getBatch().toApi().copy(numberOfTeachers = 1)),
            teacherController.getTeacherBatches(TEST_ID))
    }

// TODO: fix UT
//    @Test
//    @WithMockUser(username = TEST_ID, roles = ["SUPER"])
//    fun getTeacherCourses_shouldGetTeacherCourses_test() = runBlocking {
//        instituteRepository.save(instituteUtil.getInstitute())
//        courseRepository.save(instituteUtil.getCourse())
//        batchRepository.save(instituteUtil.getBatch())
//        instituteController.addTeacherToBatch(
//            TEST_ID,
//            TEST_ID,
//            TEST_ID
//        )
//        Assertions.assertEquals(listOf(instituteUtil.getCourse().toApi()), teacherController.getTeacherCourses(TEST_ID))
//    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["SUPER"])
    fun getTeacherInstitutes_shouldGetTeacherInstitutes_test() = runBlocking {
        instituteRepository.save(instituteUtil.getInstitute())
        courseRepository.save(instituteUtil.getCourse())
        instituteController.addTeacherToInstitute(
            TEST_ID,
            TEST_ID
        )
        Assertions.assertEquals(
            listOf(instituteUtil.getInstitute().toApi()),
            teacherController.getTeacherInstitutes(TEST_ID)
        )
    }

    @Test
    @WithMockUser(username = InstituteControllerTest.TEST_ID, roles = ["SUPER"])
    fun getUserInstituteAndRole_test() = runBlocking {
        createTeacher_shouldCreateTeacherWithCorrectTeacherInfo_test()
        instituteRepository.save(instituteUtil.getInstitute())
        courseRepository.save(instituteUtil.getCourse())
        instituteController.addTeacherToInstitute(
            TEST_ID,
            TEST_ID
        )
        Assertions.assertEquals(setOf(instituteUtil.getUserRoleDetails_Teacher()),instituteController.getUserInstituteAndRole(TEST_ID))
    }

}