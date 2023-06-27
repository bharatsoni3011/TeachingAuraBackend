package com.teachingaura.service.impl

import com.google.gson.Gson
import com.teachingaura.api.TestV2Details
import com.teachingaura.db.TestV2Repository
import com.teachingaura.db.model.BatchTest
import com.teachingaura.exception.BatchDoesNotExistException
import com.teachingaura.exception.TestDoesNotExistsException
import com.teachingaura.service.CourseService
import com.teachingaura.service.InstituteService
import com.teachingaura.service.TestV2Service
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.inject.Inject

@Service
class TestV2ServiceImpl @Inject constructor(
    @Lazy private val instituteService: InstituteService,
    private val testV2Repository: TestV2Repository,
    private val courseService: CourseService,
) : TestV2Service {

    override suspend fun getBatchTestById(instituteId: String, batchId: String, testId: String): BatchTest {
        val tests = getAllBatchTests(instituteId, batchId)
        return tests.find { it.pid == testId } ?: throw TestDoesNotExistsException()
    }

    override suspend fun getAllBatchTests(instituteId: String, batchId: String): List<BatchTest> {
        val institute = instituteService.getInstituteById(instituteId)
        val batch = institute.batches.find { it.pid == batchId } ?: throw BatchDoesNotExistException()
        return batch.testsV2
    }

    override suspend fun createBatchTest(
        instituteId: String,
        batchId: String,
        testV2Details: TestV2Details
    ): BatchTest {
        val institute = instituteService.getInstituteById(instituteId)
        val batch = institute.batches.find { it.pid == batchId } ?: throw BatchDoesNotExistException()
        val course = courseService.getCourseById(testV2Details.courseId)
        val gson = Gson()
        val sections = gson.toJson(testV2Details.sections)

        val test = BatchTest(
            testV2Details.id,
            testV2Details.name,
            testV2Details.description,
            testV2Details.startTime,
            testV2Details.endTime,
            course,
            batch,
            institute,
            sections
        )

        return testV2Repository.save(test)
    }

    override suspend fun updateBatchTest(
        instituteId: String,
        batchId: String,
        testId: String,
        testV2Details: TestV2Details
    ): BatchTest {

        val test = getBatchTestById(instituteId, batchId, testId)
        test.name = testV2Details.name
        test.description = testV2Details.description
        test.startTime = testV2Details.startTime
        test.endTime = testV2Details.endTime

        testV2Repository.save(test)
        return test
    }

    @Transactional
    override suspend fun deleteBatchTest(instituteId: String, batchId: String, testId: String): BatchTest {
        val test = getBatchTestById(instituteId, batchId, testId)
        testV2Repository.delete(test)
        return test
    }

}