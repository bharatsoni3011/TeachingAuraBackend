package com.teachingaura.service.impl

import com.teachingaura.api.SubmissionDetails
import com.teachingaura.db.AttachmentRepository
import com.teachingaura.db.SubmissionRepository
import com.teachingaura.db.TestAttachmentRepository
import com.teachingaura.db.model.BaseSubmission
import com.teachingaura.db.model.Submission
import com.teachingaura.db.model.TestAttachment
import com.teachingaura.exception.SubmissionDoesNotExistsException
import com.teachingaura.service.StudentService
import com.teachingaura.service.SubmissionService
import com.teachingaura.service.TestService
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SubmissionServiceImpl @Inject constructor(
    private val submissionRepository: SubmissionRepository,
    private val studentService: StudentService,
    private val testService: TestService,
    private val testAttachmentRepository: TestAttachmentRepository,
    private val attachmentRepository: AttachmentRepository
) : SubmissionService {

    override suspend fun getSubmission(studentId: String, testId: String, submissionId: String): BaseSubmission {
        val test = testService.findById(testId)
        return test.submissions.find { it.pid == submissionId } ?: throw SubmissionDoesNotExistsException()
    }

    override suspend fun addSubmission(
        studentId: String,
        testId: String,
        submissionDetails: SubmissionDetails
    ): BaseSubmission {

        val student = studentService.getStudent(studentId)
        val test = testService.findById(testId)

        val testAttachments =submissionDetails.testAttachmentDetails.map {

            val attachments = it.attachments.mapNotNull { attachmentRepository.findByPid(it.id) }

            val testAttachment = TestAttachment(
                it.id,
                attachments,
                it.typeOfTestAttachment
            )
            testAttachmentRepository.save(testAttachment)
        }

        val submission = Submission(
                submissionDetails.id,
                test.institute,
                test.batch,
                student,
                submissionDetails.submissionTime,
                testAttachments,
                submissionDetails.marks,
                test
        )

        submissionRepository.save(submission)

        return submission

    }

    override suspend fun getAllSubmissions(instituteId: String, testId: String): List<BaseSubmission> {
        val test = testService.findById(testId)
        return test.submissions
    }
}
