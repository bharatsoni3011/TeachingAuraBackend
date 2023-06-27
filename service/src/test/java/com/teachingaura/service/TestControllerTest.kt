package com.teachingaura.service

import com.teachingaura.IntegrationBaseTest
import com.teachingaura.api.*
import com.teachingaura.controller.SubmissionController
import com.teachingaura.controller.TestController
import com.teachingaura.db.*
import com.teachingaura.db.model.*
import com.teachingaura.exception.TestDoesNotExistsException
import com.teachingaura.util.InstituteUtil
import com.teachingaura.util.TestUtil
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class TestControllerTest : IntegrationBaseTest() {

    @Autowired
    private lateinit var testController: TestController

    @Autowired
    private lateinit var testRepository: TestRepository

    @Autowired
    private lateinit var testAttachmentRepository: TestAttachmentRepository

    @Autowired
    private lateinit var instituteRepository: InstituteRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var batchRepository: BatchRepository

    @Autowired
    private lateinit var submissionController: SubmissionController

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var submissionRepository: SubmissionRepository

    @Autowired
    private lateinit var testUtil: TestUtil

    @Autowired
    private lateinit var instituteUtil: InstituteUtil

    private lateinit var testDetails: TestDetails

    @BeforeEach
    fun init() {
        instituteRepository.save(instituteUtil.getInstitute())
        courseRepository.save(instituteUtil.getCourse())
        batchRepository.save(instituteUtil.getBatch())
    }

    @AfterEach
    fun cleanup() {
        submissionRepository.deleteAll()
        testRepository.deleteAll()
        testAttachmentRepository.deleteAll()
        batchRepository.deleteAll()
        courseRepository.deleteAll()
        instituteRepository.deleteAll()
        studentRepository.deleteAll()
    }

    companion object {
        const val TEST_ID = "test_id"
        const val TEST_NAME = "Test Name"
        const val TEST_LOGO = "AIMT_url"
        const val TEST_CONTACT_NUMBER = "1234567"
        const val TEST_GMAIL_COM = "test@gmail.com"
        const val TEST_DESCRIPTION = "Test Description"
        var START_TIME = LocalDateTime.now().toLocalDate().toEpochDay()
        var END_TIME = LocalDateTime.now().toLocalDate().toEpochDay()
        const val TEST_URL = "Test URL"
        const val TEST_MARKS = 50L
    }

    @Test
    fun getAllTests_shouldGetCorrectTestInfo_test() = runBlocking {
        createTest_shouldCreateTestWithCorrectInfo_test()
        Assertions.assertEquals(listOf(testUtil.getTestDetails()), testController.getAllTests(TEST_ID, TEST_ID))
    }

    @Test
    fun getTestsById_shouldGetCorrectTestInfo_test() = runBlocking {
        createTest_shouldCreateTestWithCorrectInfo_test()
        Assertions.assertEquals(testUtil.getTestDetails(), testController.getTestById(TEST_ID, TEST_ID, TEST_ID))
    }

    @Test
    fun createTest_shouldCreateTestWithCorrectInfo_test() = runBlocking {
        testController.createTest(TEST_ID, TEST_ID, testUtil.getTestDetails())
        Assertions.assertEquals(testUtil.getTestDetails(), testController.getTestById(TEST_ID, TEST_ID, TEST_ID))
    }

    @Test
    fun createTestWithoutAttachment_shouldCreateTestWithCorrectInfo_test() = runBlocking {
        testController.createTest(TEST_ID, TEST_ID, testUtil.getTestDetailsWithoutTestAttachment())
        Assertions.assertEquals(
            testUtil.getTestDetailsWithoutTestAttachment(),
            testController.getTestById(TEST_ID, TEST_ID, TEST_ID)
        )
    }

    @Test
    fun updateTest_shouldUpdateTestWithCorrectInfo_test() = runBlocking {
        //attachmentDetails = attachmentController.generateSignedUrlToUpload(TEST_ID, getAttachmentDetails())
        testController.createTest(TEST_ID, TEST_ID, testUtil.getTestDetails())
        testDetails = TestDetails(
            TEST_ID,
            "Test Name Update",
            "Test Description Update",
            START_TIME,
            END_TIME,
            TEST_ID,
            listOf(testUtil.getTestAttachmentDetails())
        )
        testController.updateTest(TEST_ID, TEST_ID, TEST_ID, testDetails)
        Assertions.assertEquals(testDetails, testController.getTestById(TEST_ID, TEST_ID, TEST_ID))
    }

    @Test
    fun deleteTest_shouldCreateTestWithCorrectInfo_test() {
        Assertions.assertThrows(TestDoesNotExistsException::class.java) {
            runBlocking {
                createTest_shouldCreateTestWithCorrectInfo_test()
                testController.deleteTest(TEST_ID, TEST_ID, TEST_ID)
                testController.getTestById(TEST_ID, TEST_ID, TEST_ID)
            }
        }
    }

    @Test
    fun addSubmission_shouldAddSubmissionWithCorrectInfo_test() = runBlocking {
        createTest_shouldCreateTestWithCorrectInfo_test()
        studentRepository.save(instituteUtil.getStudent())
        submissionController.addSubmission(TEST_ID, TEST_ID, testUtil.getSubmissionDetails())
        Assertions.assertEquals(
            testUtil.getSubmissionDetails(),
            submissionController.getSubmission(TEST_ID, TEST_ID, TEST_ID)
        )
    }


}