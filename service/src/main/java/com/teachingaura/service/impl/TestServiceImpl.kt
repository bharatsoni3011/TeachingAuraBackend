package com.teachingaura.service.impl

import com.teachingaura.api.TestDetails
import com.teachingaura.db.AttachmentRepository
import com.teachingaura.db.TestAttachmentRepository
import com.teachingaura.db.TestRepository
import com.teachingaura.db.model.Test
import com.teachingaura.db.model.TestAttachment
import com.teachingaura.exception.BatchDoesNotExistException
import com.teachingaura.exception.TestDoesNotExistsException
import com.teachingaura.service.InstituteService
import com.teachingaura.service.StudentService
import com.teachingaura.service.TeacherService
import com.teachingaura.service.TestService
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class TestServiceImpl @Inject constructor(
    private val testRepository: TestRepository,
    @Lazy private val instituteService: InstituteService,
    private val testAttachmentRepository: TestAttachmentRepository,
    private val attachmentRepository: AttachmentRepository,
) : TestService {

    override suspend fun getTestById(instituteId: String, batchId: String, testId: String): Test {
        val tests = getAllTests(instituteId, batchId)
        return tests.find { it.pid == testId } ?: throw TestDoesNotExistsException()
    }

    override suspend fun getAllTests(instituteId: String, batchId: String): List<Test> {
        val institute = instituteService.getInstituteById(instituteId)
        val batch = institute.batches.find { it.pid == batchId } ?: throw BatchDoesNotExistException()
        return batch.tests
    }

    override suspend fun createTest(instituteId: String, batchId: String, testDetails: TestDetails): Test {
        val institute = instituteService.getInstituteById(instituteId)
        val batch = institute.batches.find { it.pid == batchId } ?: throw BatchDoesNotExistException()

        val testAttachments = testDetails.testAttachmentDetails.filter { it.id != "" }.map {

            val attachment = it.attachments.mapNotNull { attachmentRepository.findByPid(it.id) }

            val testAttachment = TestAttachment(
                it.id,
                attachment,
                it.typeOfTestAttachment
            )
            testAttachmentRepository.save(testAttachment)
        }

        val test = Test(
            testDetails.id,
            testDetails.name,
            testDetails.description,
            testDetails.startTime,
            testDetails.endTime,
            testAttachments.toMutableList(),
            institute,
            batch
        )

        testRepository.save(test)
        return test
    }

    override suspend fun updateTest(
        instituteId: String,
        batchId: String,
        testId: String,
        testDetails: TestDetails
    ): Test {
        val test = getTestById(instituteId, batchId, testId)
        test.name = testDetails.name
        test.description = testDetails.description
        test.startTime = testDetails.startTime
        test.endTime = testDetails.endTime
        val testAttachments = testDetails.testAttachmentDetails.filter { it.id != "" }.map {

            val attachment = it.attachments.mapNotNull { attachmentRepository.findByPid(it.id) }

            val testAttachment = TestAttachment(
                it.id,
                attachment,
                it.typeOfTestAttachment
            )
            testAttachmentRepository.save(testAttachment)
        }
        test.testAttachments = testAttachments.toMutableList()
        testRepository.save(test)
        return test
    }

    override suspend fun deleteTest(instituteId: String, batchId: String, testId: String): Test {
        val test = getTestById(instituteId, batchId, testId)
        testRepository.delete(test)
        testAttachmentRepository.deleteAll(test.testAttachments)
        test.testAttachments.map { attachmentRepository.deleteAll(it.attachments) }
        return test
    }

    override suspend fun findById(testId: String): Test {
        return testRepository.findByPid(testId) ?: throw TestDoesNotExistsException()
    }

}