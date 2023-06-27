package com.teachingaura.util

import com.teachingaura.api.*
import com.teachingaura.api.test.QuestionDetails
import com.teachingaura.api.test.DataFormat
import com.teachingaura.db.model.*
import com.teachingaura.roles.Role
import com.teachingaura.service.InstituteControllerTest
import com.teachingaura.service.StudentControllerTest
import com.teachingaura.service.TeacherControllerTest
import com.teachingaura.toApi
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class InstituteUtil {

    fun getInstitute(): Institute {
        return Institute(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_LOGO,
            InstituteControllerTest.TEST_GMAIL_COM,
            InstituteControllerTest.TEST_CONTACT_NUMBER,
            InstituteControllerTest.ABOUT_US,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_GMAIL_COM
        )
    }

    fun getCourse(): Course {
        return Course(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_DESCRIPTION,
            getInstitute()
        )
    }

    fun getCourseDetail(): CourseDetails {
        return CourseDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_DESCRIPTION,
            listOf(
                getSubjectDetail()
            ),
            InstituteControllerTest.TEST_ID,
        )
    }

    fun getUpdateCourseDetail(): CourseDetails {
        return CourseDetails(
            InstituteControllerTest.TEST_ID,
            "Update Name",
            InstituteControllerTest.TEST_DESCRIPTION,
            listOf(
                getSubjectDetail()
            ),
            InstituteControllerTest.TEST_ID,
        )
    }

    fun getSubjectDetail(): SubjectDetails {
        return SubjectDetails(
            InstituteControllerTest.TEST_ID, InstituteControllerTest.TEST_NAME, listOf(
                TopicDetails(InstituteControllerTest.TEST_ID, InstituteControllerTest.TEST_NAME)
            )
        )
    }

    fun getBatch(): Batch {
        return Batch(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            LocalDateTime.now().toLocalDate().toEpochDay(),
            true,
            "",
            BatchType.ONLINE,
            getCourse(),
            getInstitute()
        )
    }

    fun getStudent(): Student {
        return Student(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_GMAIL_COM,
            InstituteControllerTest.TEST_CONTACT_NUMBER,
            StudentControllerTest.TEST_DATE_OF_BIRTH,
            MaxEducation.BELOW_MATRICULATE,
        )
    }

    fun getStudentWithEmptyInstitute(): Student {
        return Student(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_GMAIL_COM,
            InstituteControllerTest.TEST_CONTACT_NUMBER,
            StudentControllerTest.TEST_DATE_OF_BIRTH,
            MaxEducation.BELOW_MATRICULATE
        )
    }

    fun getTeacherWithEmptyInstitute(): Teacher {
        return Teacher(
            TeacherControllerTest.TEST_ID,
            TeacherControllerTest.TEST_NAME,
            TeacherControllerTest.TEST_CONTACT_NUMBER,
            TeacherControllerTest.TEST_GMAIL_COM,
            TeacherControllerTest.TEST_SUBJECT,
        )
    }

    fun getTeacher(): Teacher {
        return Teacher(
            TeacherControllerTest.TEST_ID,
            TeacherControllerTest.TEST_NAME,
            TeacherControllerTest.TEST_CONTACT_NUMBER,
            TeacherControllerTest.TEST_GMAIL_COM,
            TeacherControllerTest.TEST_SUBJECT,
        )
    }

    fun getNoticeDetails(): NoticeDetails {
        return NoticeDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            listOf(getAttachment().toApi()),
            listOf(getBatch().toApi())
        )
    }

    fun getNoticeDetails(noticeDetails: NoticeDetails): NoticeDetails {
        return NoticeDetails(
            noticeDetails.id,
            noticeDetails.content,
            noticeDetails.attachmentDetailsList,
            noticeDetails.batches,
        )
    }

    fun getNoticeDetailsWithoutAttachment(): NoticeDetails {
        return NoticeDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            listOf()
        )
    }

    fun getNoticeDetailsWithoutAttachment(noticeDetails: NoticeDetails): NoticeDetails {
        return NoticeDetails(
            noticeDetails.id,
            noticeDetails.content,
            listOf()
        )
    }

    fun getAttachment(): Attachment {
        return Attachment(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_URL,
            InstituteControllerTest.TEST_NAME,
            AttachmentType.IMAGE,
            getInstitute()
        )
    }

    fun getStudyMaterialDetails(): StudyMaterialDetails {
        return StudyMaterialDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            listOf(getAttachment().toApi()),
            InstituteControllerTest.TEST_ID
        )
    }

    fun getAnnouncementDetails(): AnnouncementDetails {
        return AnnouncementDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            listOf(getAttachment().pid),
            listOf(getBatch().pid)
        )
    }

    fun getAnnouncementDetailsWithoutAttachment(): AnnouncementDetails {
        return AnnouncementDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            emptyList(),
            listOf()
        )
    }

    fun getCourseDetails(): CourseDetails {
        return CourseDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_DESCRIPTION,
            listOf(
                SubjectDetails(
                    InstituteControllerTest.TEST_ID,
                    InstituteControllerTest.TEST_NAME,
                    listOf(
                        TopicDetails(
                            InstituteControllerTest.TEST_ID,
                            InstituteControllerTest.TEST_NAME,
                            0,
                            InstituteControllerTest.TEST_DESCRIPTION
                        )
                    )
                )
            ),
            InstituteControllerTest.TEST_ID
        )
    }

    fun getTopicAddDetails(): TopicAddDetails {

        val testFile = TopicFileDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_URL,
            0
        )

        val testText = TopicTextDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            0
        )

        val testQuiz = TopicQuizDetails(
            InstituteControllerTest.TEST_ID,
            0,
            listOf(
                QuestionDetails("Question-1", listOf(OptionDetails("Option1", false), OptionDetails("Option2", true)), DataFormat.TEXT),
                QuestionDetails("Question-2", listOf(OptionDetails("Option1", false)), DataFormat.BASE64)
            )
        )

        return TopicAddDetails(listOf(testQuiz), listOf(testText), listOf(testFile))
    }

    fun getCourseV2Details(): CourseDetails {
        return CourseDetails(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_DESCRIPTION,
            listOf(
                SubjectDetails(
                    InstituteControllerTest.TEST_ID,
                    InstituteControllerTest.TEST_NAME,
                    listOf(
                        TopicDetails(
                            InstituteControllerTest.TEST_ID,
                            InstituteControllerTest.TEST_NAME,
                            0,
                            InstituteControllerTest.TEST_DESCRIPTION
                        )
                    )
                )
            ),
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_URL,
            CourseType.OFFLINE,
            300,
            6L,
            )
    }

    fun getTestV2Details(): TestV2Details {
        return TestV2Details(
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_NAME,
            InstituteControllerTest.TEST_DESCRIPTION,
            LocalDateTime.now().toLocalDate().toEpochDay(),
            LocalDateTime.now().toLocalDate().toEpochDay(),
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_ID,
            InstituteControllerTest.TEST_ID,
            listOf(getSectionDetails())
        )
    }

    fun getSectionDetails(): SectionDetails {
        return SectionDetails(
            InstituteControllerTest.TEST_NAME,
            LocalDateTime.now().toLocalDate().toEpochDay(),
            listOf(QuestionDetails("Question-1", listOf(OptionDetails("Option1", false), OptionDetails("Option2", true))),
                QuestionDetails("Question-2", listOf(OptionDetails("Option1", false))))
        )
    }

    fun getUserRoleDetails_Teacher(): UserRoleDetails {
        return UserRoleDetails(InstituteControllerTest.TEST_NAME, Role.ROLE_TEACHER.toString())
    }

    fun getUserRoleDetails_Student(): UserRoleDetails {
        return UserRoleDetails(InstituteControllerTest.TEST_NAME, Role.ROLE_STUDENT.toString())
    }
}
