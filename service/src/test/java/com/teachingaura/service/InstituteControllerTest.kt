package com.teachingaura.service

import com.teachingaura.IntegrationBaseTest
import com.teachingaura.api.*
import com.teachingaura.controller.InstituteController
import com.teachingaura.controller.StudentController
import com.teachingaura.controller.TeacherController
import com.teachingaura.controller.TestV2Controller
import com.teachingaura.db.*
import com.teachingaura.db.model.*
import com.teachingaura.exception.InstituteAlreadyExistsException
import com.teachingaura.exception.InstituteDoesNotExistsException
import com.teachingaura.toApi
import com.teachingaura.util.InstituteUtil
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithMockUser

class InstituteControllerTest : IntegrationBaseTest() {

    @Autowired
    private lateinit var instituteController: InstituteController

    @Autowired
    private lateinit var instituteRepository: InstituteRepository

    @Autowired
    private lateinit var courseRepository: CourseRepository

    @Autowired
    private lateinit var subjectRepository: SubjectRepository

    @Autowired
    private lateinit var topicRepository: TopicRepository

    @Autowired
    private lateinit var batchRepository: BatchRepository

    @Autowired
    private lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var studentController: StudentController

    @Autowired
    private lateinit var teacherRepository: TeacherRepository

    @Autowired
    private lateinit var attachmentRepository: AttachmentRepository

    @Autowired
    private lateinit var announcementsRepository: AnnouncementsRepository

    @Autowired
    private lateinit var studyMaterialRepository: StudyMaterialRepository

    @Autowired
    private lateinit var studentInvitesRepository: StudentInvitesRepository

    @Autowired
    private lateinit var teacherInvitesRepository: TeacherInvitesRepository

    @Autowired
    private lateinit var teacherController: TeacherController

    @Autowired
    private lateinit var instituteUtil: InstituteUtil

    @Autowired
    private lateinit var topicFileRepository: TopicFileRepository

    @Autowired
    private lateinit var topicTextRepository: TopicTextRepository

    @Autowired
    private lateinit var topicQuizRepository: TopicQuizRepository

    @Autowired
    private lateinit var testV2Controller: TestV2Controller

    @Autowired
    private lateinit var testV2Repository: TestV2Repository

    private lateinit var testInstitute: Institute
    private lateinit var testInstituteDetails: InstituteDetails
    private lateinit var testCourse: Course
    private lateinit var testCourseDetails: CourseDetails
    private lateinit var testBatch: Batch
    private lateinit var testStudent: Student
    private lateinit var testTeacher: Teacher
    private lateinit var testNoticeDetails: NoticeDetails
    private lateinit var testStudyMaterialDetails: StudyMaterialDetails
    private lateinit var testAnnouncementDetails: AnnouncementDetails

    @BeforeEach
    fun init() {
        testInstitute = instituteUtil.getInstitute()
        testTeacher = instituteUtil.getTeacher()
        testStudent = instituteUtil.getStudent()
        testCourse = instituteUtil.getCourse()
        testBatch = instituteUtil.getBatch()
        instituteRepository.save(testInstitute)
    }

    @AfterEach
    fun cleanup() {
        studentInvitesRepository.deleteAll()
        teacherInvitesRepository.deleteAll()
        announcementsRepository.deleteAll()
        attachmentRepository.deleteAll()
        studyMaterialRepository.deleteAll()
        testV2Repository.deleteAll()
        batchRepository.deleteAll()
        topicFileRepository.deleteAll()
        topicQuizRepository.deleteAll()
        topicTextRepository.deleteAll()
        topicRepository.deleteAll()
        subjectRepository.deleteAll()
        courseRepository.deleteAll()
        studentRepository.deleteAll()
        teacherRepository.deleteAll()
        instituteRepository.deleteAll()
    }

    companion object {
        const val TEST_ID = "test_id"
        const val TEST_NAME = "Test Name"
        const val TEST_LOGO = "AIMT_url"
        const val TEST_CONTACT_NUMBER = "1234567"
        const val TEST_GMAIL_COM = "test@gmail.com"
        const val TEST_URL = "Test URL"
        const val TEST_DESCRIPTION = "Test Description"
        const val ABOUT_US = "about_us"
    }

    @Test
    fun createInstitute_shouldCreateInstituteWithCorrectInstituteInfo_test() = runBlocking {
        instituteRepository.deleteAll()
        instituteController.createInstitute(
            testInstitute.toApi()
        )
        Assertions.assertEquals(testInstitute.toApi(), instituteController.getInstituteById(TEST_ID))
    }

    @Test
    fun createInstitute_shouldThrowInstituteAlreadyExistsException_whenUserIsAlreadyPresent_test() {
        Assertions.assertThrows(InstituteAlreadyExistsException::class.java) {
            runBlocking {
                instituteController.createInstitute(
                    testInstitute.toApi()
                )
            }
        }
    }

    @Test
    fun getAllInstitutes_shouldReturnAllInstitutes_test() = runBlocking {
        Assertions.assertEquals(listOf(testInstitute.toApi()), instituteController.getAllInstitutes())
    }

    @Test
    fun getInstituteById_shouldReturnCorrectInstituteInfo_test() = runBlocking {
        Assertions.assertEquals(testInstitute.toApi(), instituteController.getInstituteById(TEST_ID))
    }

    @Test
    fun getInstituteById_shouldThrowInstituteDoesNotExistException_whenInstituteDoesNotExist_test() {
        instituteRepository.deleteAll()
        Assertions.assertThrows(InstituteDoesNotExistsException::class.java) {
            runBlocking {
                instituteController.getInstituteById(TEST_ID)
            }
        }
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun createCourse_shouldCreateCourseWithCorrectCourseInfo_test() = runBlocking {
        instituteController.createCourse(TEST_ID, testCourse.toApi())
        Assertions.assertEquals(testCourse.toApi(), instituteController.getCourseById(TEST_ID, TEST_ID))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun updateCourse_shouldUpdateCourseWithCorrectCourseInfo_test() = runBlocking {
        testCourseDetails = instituteController.createCourse(TEST_ID, instituteUtil.getCourseDetail())
        instituteController.createBatch(TEST_ID, testBatch.toApi())
        testStudyMaterialDetails = instituteUtil.getStudyMaterialDetails()
        instituteController.createCourseStudyMaterial(
            testInstitute.pid,
            testCourseDetails.id,
            testStudyMaterialDetails
        )
        instituteController.updateCourse(
            TEST_ID, TEST_ID, instituteUtil.getUpdateCourseDetail()
        )
        Assertions.assertEquals(
            instituteUtil.getUpdateCourseDetail(), instituteController.getCourseById(TEST_ID, TEST_ID)
        )

        Assertions.assertNotNull(instituteController.getCourseStudyMaterial(testInstitute.pid, testCourseDetails.id))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun updateInstitute_shouldUpdateInstituteWithCorrectInstituteInfo_test() = runBlocking {
        testInstituteDetails = InstituteDetails(
            TEST_ID, "Update Name",
            "https://storage.googleapis.com/teachingaura.appspot.com/test_id/institute/profile",
            TEST_GMAIL_COM, TEST_CONTACT_NUMBER, ABOUT_US, TEST_NAME, TEST_GMAIL_COM,

            )
        instituteController.updateInstitute(
            TEST_ID, testInstituteDetails
        )
        Assertions.assertEquals(testInstituteDetails, instituteController.getInstituteById(TEST_ID))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun createBatch_shouldCreateBatchWithCorrectBatchInfo_test() = runBlocking {
        instituteController.createCourse(TEST_ID, testCourse.toApi())
        instituteController.createBatch(TEST_ID, testBatch.toApi())
        Assertions.assertEquals(testBatch.toApi(), instituteController.getBatchById(TEST_ID, TEST_ID))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun addStudentToInstitute_shouldAddStudentToInstitute_test() = runBlocking {
        studentController.createStudent(testStudent.toApi())
        instituteController.addStudentToInstitute(
            testInstitute.pid,
            testStudent.pid
        )
        Assertions.assertEquals(
            listOf(testStudent.toApi()), instituteController.getInstituteStudents(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun removeStudentFromInstitute_shouldDeleteStudentFromInstitute_test() = runBlocking {
        studentController.createStudent(testStudent.toApi())
        instituteController.sendInviteToStudent(
            testInstitute.pid,
            testStudent.pid
        )
        instituteController.removeStudentFromInstitute(
            testInstitute.pid,
            testStudent.pid
        )
        Assertions.assertEquals(
            emptyList<StudentDetails>(), instituteController.getInstituteStudents(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun addTeacherToInstitute_shouldAddTeacherToInstitute_test() = runBlocking {
        teacherController.createTeacher(testTeacher.toApi())
        instituteController.addTeacherToInstitute(
            testInstitute.pid,
            testTeacher.pid
        )
        Assertions.assertEquals(
            listOf(testTeacher.toApi()), instituteController.getInstituteTeachers(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun removeTeacherFromInstitute_shouldRemoveTeacherFromInstitute_test() = runBlocking {
        teacherController.createTeacher(testTeacher.toApi())
        instituteController.sendInviteToTeacher(
            testInstitute.pid,
            testTeacher.pid
        )
        teacherController.acceptInstituteInvite(testTeacher.pid, testInstitute.pid)
        instituteController.removeTeacherFromInstitute(
            testInstitute.pid,
            testTeacher.pid
        )
        Assertions.assertEquals(
            emptyList<TeacherDetails>(), instituteController.getInstituteTeachers(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun addStudentToBatch_shouldAddStudentToBatch_test() = runBlocking {
        studentController.createStudent(testStudent.toApi())
        instituteController.createCourse(TEST_ID, testCourse.toApi())
        instituteController.createBatch(TEST_ID, testBatch.toApi())
        instituteController.addStudentToBatch(
            testInstitute.pid,
            testStudent.pid,
            testBatch.pid
        )
        Assertions.assertEquals(
            listOf(testBatch.toApi().copy(numberOfStudents = 1)), studentController.getStudentEnrolledBatches(testStudent.pid)
        )
        studentRepository.deleteAll()
    }

    @Test
    fun getInstituteOverview_shouldGetInstituteOverview_test() = runBlocking {
        Assertions.assertEquals(
            Overview(
                testInstitute.pid,
                testInstitute.name,
                testInstitute.logo,
                0L,
                0L,
                0L,
                0L,
                0L,
                0L,
                0L
            ), instituteController.getInstituteOverview(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getInstituteTeachers_shouldFetchInstituteTeachers_test() = runBlocking {
        teacherController.createTeacher(testTeacher.toApi())
        instituteController.addTeacherToInstitute(testInstitute.pid, testTeacher.pid)
        Assertions.assertEquals(
            listOf(testTeacher.toApi()),
            instituteController.getInstituteTeachers(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getInstituteStudents_shouldFetchInstituteStudents_test() = runBlocking {
        studentRepository.save(testStudent)
        instituteController.addStudentToInstitute(testInstitute.pid, testStudent.pid)
        Assertions.assertEquals(
            listOf(testStudent.toApi()),
            instituteController.getInstituteStudents(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getInstituteCourses_shouldFetchInstituteCourses_test() = runBlocking {
        testCourseDetails = instituteController.createCourse(TEST_ID, instituteUtil.getCourse().toApi())
        Assertions.assertEquals(
            listOf(instituteUtil.getCourse().toApi()),
            instituteController.getInstituteCourses(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getInstituteBatches_shouldFetchInstituteBatches_test() = runBlocking {
        instituteController.createCourse(TEST_ID, instituteUtil.getCourse().toApi())
        instituteController.createBatch(TEST_ID, instituteUtil.getBatch().toApi())
        Assertions.assertEquals(listOf(testBatch.toApi()),
            instituteController.getInstituteBatches(testInstitute.pid))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun createAnnouncement_shouldCreateAnnouncement_test() = runBlocking {
        testAnnouncementDetails = instituteUtil.getAnnouncementDetails()
        attachmentRepository.save(instituteUtil.getAttachment())
        instituteController.createCourse(TEST_ID, testCourse.toApi())
        instituteController.createBatch(TEST_ID, testBatch.toApi())
        instituteController.createAnnouncement(testInstitute.pid, testAnnouncementDetails)
        Assertions.assertEquals(
            instituteUtil.getNoticeDetails(), instituteUtil.getNoticeDetails(
                instituteController.getAnnouncementById(
                    testInstitute.pid,
                    testAnnouncementDetails.id
                )
            )
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getInstituteAnnouncements_shouldFetchInstituteAnnouncements_test() = runBlocking {
        testAnnouncementDetails = instituteUtil.getAnnouncementDetails()
        attachmentRepository.save(instituteUtil.getAttachment())
        instituteController.createCourse(TEST_ID, testCourse.toApi())
        instituteController.createBatch(TEST_ID, testBatch.toApi())
        instituteController.createAnnouncement(testInstitute.pid, testAnnouncementDetails)
        Assertions.assertEquals(
            listOf(instituteUtil.getNoticeDetails()),
            instituteController.getInstituteAnnouncements(testInstitute.pid).map { instituteUtil.getNoticeDetails(it) }
                .toList()
        )

    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun createCourseStudyMaterial_shouldCreateCourseStudyMaterial_test() = runBlocking {
        testCourseDetails = instituteController.createCourse(TEST_ID, instituteUtil.getCourseDetail())
        instituteController.createBatch(TEST_ID, testBatch.toApi())
        testStudyMaterialDetails = instituteUtil.getStudyMaterialDetails()
        instituteController.createCourseStudyMaterial(
            testInstitute.pid,
            testCourseDetails.id,
            testStudyMaterialDetails
        )
        Assertions.assertEquals(
            listOf(
                StudyMaterialDetails(
                    TEST_ID,
                    TEST_NAME,
                    emptyList(),
                    TEST_ID
                )
            ), instituteController.getCourseStudyMaterial(testInstitute.pid, testCourseDetails.id)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun sendInviteToStudent_shouldSendInviteToStudent_test() = runBlocking {
        studentController.createStudent(testStudent.toApi())
        instituteController.sendInviteToStudent(
            testInstitute.pid,
            testStudent.pid
        )
        Assertions.assertEquals(
            listOf(StudentInviteDetails(testInstitute.toApi(), testStudent.toApi(), InviteType.INVITED)),
            instituteController.getStudentInvites(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getStudentInvites_shouldGetStudentInvites_test() = runBlocking {
        studentController.createStudent(testStudent.toApi())
        instituteController.sendInviteToStudent(testInstitute.pid, testStudent.pid)
        Assertions.assertEquals(
            listOf(StudentInviteDetails(testInstitute.toApi(), testStudent.toApi(), InviteType.INVITED)),
            instituteController.getStudentInvites(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun sendInviteToTeacher_shouldSendInviteToTeacher_test() = runBlocking {
        teacherController.createTeacher(testTeacher.toApi())
        instituteController.sendInviteToTeacher(
            testInstitute.pid,
            testTeacher.pid
        )
        Assertions.assertEquals(
            listOf(TeacherInviteDetails(testInstitute.toApi(), testTeacher.toApi(), InviteType.INVITED)),
            instituteController.getTeacherInvites(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getTeacherInvites_shouldGetTeacherInvites_test() = runBlocking {
        teacherController.createTeacher(testTeacher.toApi())
        instituteController.sendInviteToTeacher(testInstitute.pid, testTeacher.pid)
        Assertions.assertEquals(
            listOf(TeacherInviteDetails(testInstitute.toApi(), testTeacher.toApi(), InviteType.INVITED)),
            instituteController.getTeacherInvites(testInstitute.pid)
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getStudentBatches_shouldGetStudentBatches_test() = runBlocking {
        studentController.createStudent(testStudent.toApi())
        instituteController.createCourse(TEST_ID, testCourse.toApi())
        instituteController.createBatch(TEST_ID, testBatch.toApi())
        instituteController.addStudentToBatch(
            testInstitute.pid,
            testStudent.pid,
            testBatch.pid
        )
        Assertions.assertEquals(
            listOf(getCourseAndBatchDetails()),
            instituteController.getStudentBatches(TEST_ID, TEST_ID)
        )
        studentRepository.deleteAll()
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun addTopicDetails_shouldAddTopicDetails_test() = runBlocking {
        val courseDetails = instituteUtil.getCourseDetails()
        val topicAddDetails = instituteUtil.getTopicAddDetails()
        instituteController.createCourse(TEST_ID, courseDetails)
        instituteController.addTopicDetails(TEST_ID, TEST_ID, TEST_ID, topicAddDetails)

        Assertions.assertEquals(topicAddDetails, instituteController.getTopicDetails(TEST_ID, TEST_ID, TEST_ID))

    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun createCourseV2_shouldCreateCourseWithCorrectCourseInfo_test() = runBlocking {
        val courseV2Details = instituteUtil.getCourseV2Details()
        instituteController.createCourseV2(TEST_ID, courseV2Details)
        Assertions.assertEquals(courseV2Details, instituteController.getCourseByIdV2(TEST_ID, TEST_ID))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun searchBYCourseName_shouldSearchBYCourseNameWithCorrectCourseInfo_test() = runBlocking {
        instituteController.createCourse(TEST_ID, instituteUtil.getCourseDetail())
        Assertions.assertEquals(listOf(instituteUtil.getCourseDetail()), instituteController.searchBYCourseName(TEST_NAME))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun searchByInstituteName_shouldSearchByInstittuteNameWithCorrectInstituteInfo_test() = runBlocking {
        Assertions.assertEquals(listOf(instituteUtil.getInstitute().toApi()), instituteController.
        searchByInstituteName(TEST_NAME.substring(3)))
    }

    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun searchByInstituteName_shouldSearchByInstittuteNameReturnNull_test() = runBlocking {
        Assertions.assertEquals(listOf<InstituteDetails>(), instituteController.searchByInstituteName(TEST_ID))
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun searchBYCourseName_shouldSearchBYCourseNameReturnNull_test() = runBlocking {
        instituteController.createCourse(TEST_ID, instituteUtil.getCourseDetail())
        Assertions.assertEquals(listOf<CourseDetails>(), instituteController.searchBYCourseName(TEST_ID))
    }


    fun getCourseAndBatchDetails(): CourseAndBatchDetails {
        return CourseAndBatchDetails(
            TEST_ID,
            TEST_NAME,
            listOf(),
            TEST_ID,
            instituteUtil.getBatch().toApi().copy(numberOfStudents = 1),
            TEST_DESCRIPTION
        )
    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun createTest_shouldcreateTest_test() = runBlocking {
        createBatch_shouldCreateBatchWithCorrectBatchInfo_test()
        testV2Controller.createTest(TEST_ID, TEST_ID, instituteUtil.getTestV2Details())
        Assertions.assertEquals(instituteUtil.getTestV2Details(), testV2Controller.getTestById(TEST_ID, TEST_ID, TEST_ID))

    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun getAllTests_shouldgetAllTests_test() = runBlocking {
        createBatch_shouldCreateBatchWithCorrectBatchInfo_test()
        testV2Controller.createTest(TEST_ID, TEST_ID, instituteUtil.getTestV2Details())
        Assertions.assertEquals(listOf(instituteUtil.getTestV2Details()), testV2Controller.getAllTests(TEST_ID, TEST_ID))

    }

    @Test
    @WithMockUser(username = TEST_ID, roles = ["INSTITUTE"])
    fun deleteTest_shoulddeleteTest_test() = runBlocking {
        createBatch_shouldCreateBatchWithCorrectBatchInfo_test()
        val testV2Details = testV2Controller.createTest(TEST_ID, TEST_ID, instituteUtil.getTestV2Details())
        testV2Controller.deleteTest(TEST_ID, TEST_ID, TEST_ID)
        Assertions.assertEquals(emptyList<TestV2Details>(),testV2Controller.getAllTests(TEST_ID, TEST_ID))

    }
}