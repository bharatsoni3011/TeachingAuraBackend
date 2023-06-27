package com.teachingaura.service

import com.teachingaura.BaseTest
import com.teachingaura.db.InstituteRepository
import com.teachingaura.db.model.Institute
import com.teachingaura.exception.InstituteDoesNotExistsException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

class InstituteServiceTest : BaseTest() {

    @MockBean
    private lateinit var instituteRepository: InstituteRepository

    @Autowired
    private lateinit var instituteService: InstituteService

    private lateinit var testInstitute: Institute

    @BeforeEach
    fun init() {
        testInstitute = Institute(
            "test_id",
            "DPS",
            "DPS_LOGO",
            "connect@dps.com", 111111.toString(),
            "about-us",
            "owner_name",
            "owner_email"
        )
        Mockito.`when`(instituteRepository.findAll()).thenReturn(listOf(testInstitute))
        Mockito.`when`(instituteRepository.findByPid("test_id")).thenReturn(testInstitute)
    }

    @Test
    fun getAllInstitutes_shouldReturnAllInstitutesInfo_test() {
        runBlocking {
            Assertions.assertEquals(
                listOf(testInstitute),
                instituteService.getAllInstitutes()
            )
        }
    }

    @Test
    fun getInstitute_shouldReturnProperInstituteInfo_whenInstituteIsPresent_test() {
        runBlocking {
            Assertions.assertEquals(testInstitute, instituteService.getInstituteById("test_id"))
        }
    }

    @Test
    fun getInstitute_shouldReturnEmpty_whenInstituteIsNotPresent_test() {
        Mockito.`when`(instituteRepository.findByPid("test_id")).thenReturn(null)
        Assertions.assertThrows(InstituteDoesNotExistsException::class.java) {
            runBlocking {
                instituteService.getInstituteById("test_id")
            }
        }
    }
}
