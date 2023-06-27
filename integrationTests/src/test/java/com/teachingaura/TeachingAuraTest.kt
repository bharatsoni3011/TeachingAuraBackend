package com.teachingaura

import com.teachingaura.api.InstituteDetails
import com.teachingaura.api.InstituteService
import com.teachingaura.api.StudentService
import com.teachingaura.api.TeacherService
import com.teachingaura.api.model.TokenRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class TeachingAuraTest {

    private lateinit var studentService: StudentService
    private lateinit var teacherService: TeacherService
    private lateinit var instituteService: InstituteService

    @BeforeEach
    fun init() {
        runBlocking {
            setupFirebase()
            val firebaseApi = TeachingAuraClient.getFirebaseService()
            val tokenResponse = firebaseApi.getToken(TokenRequest("test1@springbackend.com", "test121233", true))
            TeachingAuraClient.setToken(tokenResponse.idToken)
            studentService = TeachingAuraClient.getStudentService()
            teacherService = TeachingAuraClient.getTeacherService()
            instituteService = TeachingAuraClient.getInstituteService()
        }
    }

    @Test
    fun testCreateInstitute() {
        runBlocking {
            val id = UUID.randomUUID().toString()
            val newInstitute =
                instituteService.createInstitute(InstituteDetails(id, "Test Institute"))
            Assertions.assertEquals(id, newInstitute.id)
            Assertions.assertEquals("Test Institute", newInstitute.instituteName)
        }
    }
}
