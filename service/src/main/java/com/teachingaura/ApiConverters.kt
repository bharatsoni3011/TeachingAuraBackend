package com.teachingaura

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teachingaura.api.*
import com.teachingaura.api.test.QuestionDetails
import com.teachingaura.db.model.*

fun Student.toApi(): StudentDetails {
    return StudentDetails(
        this.pid, this.name, this.email,
        this.contactNumber, this.maxEducation, this.dateOfBirth,
        "https://storage.googleapis.com/teachingaura.appspot.com/" + this.pid + "/student/profile"
    )
}

fun Institute.toApi(): InstituteDetails {
    return InstituteDetails(
        this.pid, this.name,
        "https://storage.googleapis.com/teachingaura.appspot.com/" + this.pid + "/institute/profile",
        this.email, this.contactNumber,
        this.aboutUs ?: "", this.ownerName ?: "", this.ownerEmail ?: "",

        )
}

fun Batch.toApi(): BatchDetails {
    return BatchDetails(
        this.pid, this.name, this.startDate,
        this.course.pid, this.course.name, this.institute.pid, this.active,
        this.imageUrl, this.teachers.size, this.students.size
    )
}

fun Announcement.toApi(): NoticeDetails {
    return NoticeDetails(
        this.pid, this.content, this
            .attachments
            .map { it.toApi() },
        this.batches.map { it.toApi() },
        this.createdAt
    )
}

fun Attachment.toApi(): AttachmentDetails {
    return AttachmentDetails(
        this.pid, this.name, this.url, this.attachmentType
    )
}

fun Schedule.toApi(): ScheduleDetails {
    return ScheduleDetails(
        this.pid, this.institute.pid,
        this.batch.pid, this.batch.course.pid, this.teacher?.pid, this.topic ?: "",
        this.startTime, this.endTime, this.subject?.pid ?: "", this.subject?.name ?: "",
        this.joinUrl, this.zoomMeetingId, this.imgUrl
    )
}

fun Teacher.toApi(): TeacherDetails {
    return TeacherDetails(
        this.pid, this.name, this.email,
        this.contactNumber, this.subject,
        "https://storage.googleapis.com/teachingaura.appspot.com/" + this.pid + "/teacher/profile"
    )
}

fun Subject.toApi(): SubjectDetails {
    return SubjectDetails(this.pid, this.name, this.topics.map { it.toApi() })
}

fun Topic.toApi(): TopicDetails {
    return TopicDetails(this.pid, this.name, this.orderIndex, this.description)
}

fun StudentInvites.toApi(): StudentInviteDetails {
    // TODO: optimise this to prevent sql calls
    return StudentInviteDetails(this.institute.toApi(), this.student.toApi(), this.inviteType)
}

fun TeacherInvites.toApi(): TeacherInviteDetails {
    // TODO: optimise this to prevent sql calls
    return TeacherInviteDetails(this.institute.toApi(), this.teacher.toApi(), this.inviteType)
}

fun StudyMaterial.toApi(): StudyMaterialDetails {
    return StudyMaterialDetails(this.pid, this.name, this.attachments.map { it.toApi() }, this.subject.pid)
}

fun Batch.toCourseAndBatchApi(): CourseAndBatchDetails {
    return CourseAndBatchDetails(
        this.course.pid,
        this.course.name,
        this.course.subjects.map { it.toApi() },
        this.course.institute.pid,
        this.toApi(),
        this.course.description
    )
}

fun Test.toApi(): TestDetails {
    return TestDetails(this.pid,
        this.name,
        this.description,
        this.startTime,
        this.endTime,
        this.batch.pid,
        this.testAttachments.map { it.toApi() },
        this.submissions.map { it.toApi() })
}

fun TestAttachment.toApi(): TestAttachmentDetails {
    return TestAttachmentDetails(this.pid, this.attachments.map { it.toApi() }, this.typeOfAttachment)
}

// TODO: add states in submission
fun BaseSubmission.toApi(): SubmissionDetails {
    return SubmissionDetails(this.pid, this.submissionTime, this.student.pid, this.student.name,
        this.marks, this.testAttachments.map { it.toApi() })
}

fun TopicFile.toApi(): TopicFileDetails {
    return TopicFileDetails(this.pid, this.url, this.orderIndex)
}

fun TopicText.toApi(): TopicTextDetails {
    return TopicTextDetails(this.pid, this.text, this.orderIndex)
}

fun TopicQuiz.toApi(): TopicQuizDetails {
    val gson = Gson()
    val arrayQuestionType = object : TypeToken<List<QuestionDetails>>() {}.type
    val questions: List<QuestionDetails> = gson.fromJson(this.json, arrayQuestionType)
    return TopicQuizDetails(this.pid, this.orderIndex, questions)
}

fun Course.toApi(): CourseDetails {
    return CourseDetails(
        this.pid,
        this.name,
        this.description ?: "",
        this.subjects.map { it.toApi() },
        this.institute.pid,
        this.subTitle ?: "",
        this.url ?: "",
        this.type ?: CourseType.OFFLINE,
        this.fee ?: 0L,
        this.durationInMonths ?: 0L,
    )
}

fun BatchTest.toApi(): TestV2Details {

    val gson = Gson()
    val arraySectionsType = object : TypeToken<List<SectionDetails>>() {}.type
    val sections: List<SectionDetails> = gson.fromJson(this.sections, arraySectionsType)
    return TestV2Details(
        this.pid, this.name, this.description, this.startTime, this.endTime,
        this.course.pid, this.batch.pid, this.institute.pid, sections
    )
}
