package com.teachingaura.service

import com.teachingaura.BaseTest
import com.teachingaura.api.MaxEducation
import com.teachingaura.db.ScheduleRepository
import com.teachingaura.db.StudentRepository
import com.teachingaura.db.model.*
import com.teachingaura.exception.UserAlreadyExistsException
import com.teachingaura.exception.UserDoesNotExistException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class StudentServiceTest : BaseTest() {

    @MockBean
    private lateinit var studentRepository: StudentRepository

    @MockBean
    private lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    private lateinit var studentService: StudentService
    private lateinit var testStudent: Student
    private lateinit var testBatch: Batch
    private lateinit var testInstitute: Institute
    private lateinit var testCourse: Course

    @BeforeEach
    fun init() {
        testInstitute = Institute("test_institute", "test_name", "test_logo", "test@gmail.com", "1234567","about-us","owner_name","owner_email")
        testCourse = Course("test_course", "Test Course", "Test description",institute = testInstitute)
        testStudent = Student(
            "test_id", "Test Name", "test@gmail.com", "1234567",
            LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), MaxEducation.BELOW_MATRICULATE
        )
        testBatch = Batch(
            "test_batch", "A1", 123584L, true, "", BatchType.OFFLINE,
            testCourse, testInstitute
        )
        testStudent.batches = mutableSetOf(testBatch)
        testStudent.institutes = mutableSetOf(testInstitute)
        Mockito.`when`(studentRepository.findByPid("test_id")).thenReturn(testStudent)
    }

    @Test
    fun student_shouldReturnProperStudentInfo_whenStudentIsPresent_test() {
        Assertions.assertEquals(testStudent, studentService.getStudent("test_id"))
    }

    @Test
    fun student_shouldReturnEmpty_whenStudentIsNotPresent_test() {
        Mockito.`when`(studentRepository.findByPid("test_id")).thenReturn(null)
        Assertions.assertThrows(UserDoesNotExistException::class.java) {
            studentService.getStudent("test_id")
        }
    }

    @Test
    fun batches_shouldReturnCorrectStudentBatches_test() {
        Assertions.assertEquals(listOf(testBatch), studentService.getStudentBatches("test_id"))
    }

    @Test
    fun getInstitutes_shouldReturnCorrectInstitutesList_test() {
        Assertions.assertEquals(listOf(testInstitute), studentService.getInstitutesForStudent("test_id"))
    }

    @Test
    fun getStudentCourses_shouldReturnEnrolledCoursesList_test() {
        Assertions.assertEquals(
            listOf(testBatch),
            studentService.getStudentEnrolledBatches("test_id")
        )
    }

    @Test
    @Throws(UserDoesNotExistException::class)
    fun deleteStudent_shouldDeleteStudentProperly_test() {
        studentService.deleteStudent("test_id")
        Mockito.verify(studentRepository, Mockito.times(1)).deleteById("test_id")
    }

//    @Test
//    fun getSchedule_shouldReturnCorrectStudentSchedule_test() {
//        val schedule2 = Schedule()
//        Mockito.`when`(
//            scheduleRepository.findSchedulesByBatchInAndStartTimeIsBetween(
//                List.of("test_batch"),
//                0L,
//                100L
//            )
//        )
//            .thenReturn(List.of(schedule2))
//        Assertions.assertEquals(
//            List.of(schedule2),
//            studentService.getScheduleForTimeRange("test_id", 0L, 100L)
//        )
//    }
}