package com.teachingaura.api

object API {
    const val INSTITUTE_ID = "instituteId"
    const val TEACHER_ID = "teacherId"
    const val STUDENT_ID = "studentId"
    const val TEST_ID = "testId"
    const val COURSE_ID = "courseId"
    const val BATCH_ID = "batchId"
    const val NOTICE_ID = "noticeId"
    private const val PAYMENT_ID = "paymentId"
    private const val SCHEDULE_ID = "scheduleId"
    const val ATTACHMENT_ID = "attachmentId"
    const val SUBMISSION_ID = "submissionId"
    const val MEETING_ID = "meetingId"
    const val TOPIC_ID = "topicId"
    private const val SEARCH_KEYWORD = "searchKeyword"
    private const val USER_ID = "userId"



    /**
     * Student APIs
     */
    const val CREATE_STUDENT = "students"
    const val GET_STUDENTS = "students"
    const val GET_STUDENT = "student/{${STUDENT_ID}}"
    const val DELETE_STUDENT = "student/{${STUDENT_ID}}"
    const val GET_BATCHES = "student/{${STUDENT_ID}}/batches"
    const val UPDATE_STUDENT = "student/{${STUDENT_ID}}"
    const val GET_SCHEDULE = "student/{${STUDENT_ID}}/schedule"
    const val GET_STUDENT_INSTITUTES = "student/{${STUDENT_ID}}/institutes"
    const val GET_STUDENT_ENROLLED_COURSES = "student/{${STUDENT_ID}}/courses"
    const val GET_STUDENT_ANNOUNCEMENTS = "student/{${STUDENT_ID}}/announcements"
    const val GET_INVITES_STUDENT = "student/{${STUDENT_ID}}/invites"
    const val STUDENT_ACCEPT_INSTITUTE_INVITE = "student/{${STUDENT_ID}}/institute/{${INSTITUTE_ID}}/accept"
    const val STUDENT_REJECT_INSTITUTE_INVITE = "student/{${STUDENT_ID}}/institute/{${INSTITUTE_ID}}/reject"
    const val GET_STUDENT_SCHEDULES = "student/{${STUDENT_ID}}/institute/{${INSTITUTE_ID}}/schedule"

    /**
     * Institute APIs
     */
    const val GET_INSTITUTES = "institutes"
    const val GET_INSTITUTE_BY_ID = "institute/{${INSTITUTE_ID}}"
    const val GET_INSTITUTE_BY_NAME = "institute/search"
    const val UPDATE_INSTITUTE = "institute/{${INSTITUTE_ID}}"
    const val CREATE_INSTITUTE = "institute"
    const val GET_INSTITUTE_TEACHERS = "institute/{${INSTITUTE_ID}}/teachers"
    const val GET_INSTITUTE_STUDENTS = "institute/{${INSTITUTE_ID}}/students"
    const val GET_INSTITUTE_COURSES = "institute/{${INSTITUTE_ID}}/course"
    const val GET_INSTITUTE_BATCHES = "institute/{${INSTITUTE_ID}}/batch"

    const val GET_STUDENT_BATCHES_OF_INSTITUTE = "institute/{${INSTITUTE_ID}}/student/{${STUDENT_ID}}/batch" //test missing
    const val ADD_TEACHER_TO_INSTITUTE = "institute/{${INSTITUTE_ID}}/teacher/{${TEACHER_ID}}"
    const val ADD_STUDENT_TO_INSTITUTE = "institute/{${INSTITUTE_ID}}/student/{${STUDENT_ID}}"
    const val ADD_STUDENT_TO_BATCH = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/student/{${STUDENT_ID}}"
    const val REMOVE_STUDENT_FROM_BATCH = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/student/{${STUDENT_ID}}" //missing
    const val ADD_TEACHER_TO_BATCH = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/teacher/{${TEACHER_ID}}" //missing
    const val REMOVE_TEACHER_FROM_BATCH = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/teacher/{${TEACHER_ID}}" //missing
    const val REMOVE_TEACHER_FROM_INSTITUTE = "institute/{${INSTITUTE_ID}}/teacher/{${TEACHER_ID}}"
    const val REMOVE_STUDENT_FROM_INSTITUTE = "institute/{${INSTITUTE_ID}}/student/{${STUDENT_ID}}"
    const val GET_INSTITUTE_OVERVIEW = "institute/{${INSTITUTE_ID}}/overview"
    const val INVITE_STUDENT_TO_INSTITUTE = "institute/{${INSTITUTE_ID}}/invite/student/{${STUDENT_ID}}"
    const val INVITE_TEACHER_TO_INSTITUTE = "institute/{${INSTITUTE_ID}}/invite/teacher/{${TEACHER_ID}}"
    const val INVITE_STUDENT_TO_INSTITUTE_V2 = "institute/{${INSTITUTE_ID}}/invite/student"
    const val INVITE_TEACHER_TO_INSTITUTE_V2 = "institute/{${INSTITUTE_ID}}/invite/teacher"
    const val GET_STUDENT_INVITES = "institute/{${INSTITUTE_ID}}/student/invites"
    const val GET_TEACHER_INVITES = "institute/{${INSTITUTE_ID}}/teacher/invites"
    const val DELETE_ALL_INSTITUTE_ATTACHMENTS = "institute/{${INSTITUTE_ID}}/attachment"
    const val DELETE_ALL_INSTITUTE_ANNOUNCEMENTS = "institute/{${INSTITUTE_ID}}/announcements"
    const val INSTITUTE_GET_BATCH_SCHEDULES = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/schedules"
    const val GET_ALL_BATCH_STUDENTS = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/students"
    const val GET_ALL_BATCH_TEACHERS = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/teachers"
    const val ADD_LIST_OF_STUDENTS_TO_BATCH="institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/students"
    const val ADD_LIST_OF_TEACHERS_TO_BATCH="institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/teachers"
    const val GET_INSTITUTE_AND_ROLE_BY_ID = "institute/roles/{${USER_ID}}/"

    /**
     * Teacher APIs
     */
    const val GET_TEACHER_BY_ID = "teacher/{${TEACHER_ID}}"
    const val UPDATE_TEACHER = "teacher/{${TEACHER_ID}}" //missing done
    const val CREATE_TEACHER = "teacher"
    const val TEACHERS = "teachers"
    const val GET_TEACHER_ANNOUNCEMENTS = "teacher/{${TEACHER_ID}}/announcements"
    const val GET_TEACHER_BATCHES = "teacher/{${TEACHER_ID}}/batches"
    const val GET_TEACHER_SCHEDULE = "teacher/{${TEACHER_ID}}/schedule"
    const val GET_TEACHER_COURSES = "teacher/{${TEACHER_ID}}/courses"
    const val GET_TEACHER_INSTITUTES = "teacher/{${TEACHER_ID}}/institutes"
    const val GET_INVITES_TEACHER = "teacher/{${TEACHER_ID}}/invites"
    const val TEACHER_ACCEPT_INSTITUTE_INVITE = "teacher/{${TEACHER_ID}}/institute/{${INSTITUTE_ID}}/accept"
    const val TEACHER_REJECT_INSTITUTE_INVITE = "teacher/{${TEACHER_ID}}/institute/{${INSTITUTE_ID}}/reject"
    const val GET_TEACHER_SCHEDULES = "teacher/{${TEACHER_ID}}/institute/{${INSTITUTE_ID}}/schedule"


    /**
     * Batch APIs
     */
    const val GET_BATCH_BY_ID = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}" // missing add students list
    const val CREATE_BATCH = "institute/{${INSTITUTE_ID}}/batch"
    const val GET_BATCH_STUDY_MATERIALS = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/studyMaterial"

    /**
     * Course APIs
     */
    const val GET_COURSE_BY_ID = "institute/{${INSTITUTE_ID}}/course/{${COURSE_ID}}"
    const val UPDATE_COURSE = "institute/{${INSTITUTE_ID}}/course/{${COURSE_ID}}"
    const val UPDATE_COURSE_SUBJECTS = "institute/{${INSTITUTE_ID}}/course/{${COURSE_ID}}/subjects"
    const val CREATE_COURSE = "institute/{${INSTITUTE_ID}}/course" // add list of attachments to each topic
    const val DELETE_COURSE = "institute/{${INSTITUTE_ID}}/course/{${COURSE_ID}}"
    const val ADD_TOPIC_DETAILS = "institute/{${INSTITUTE_ID}}/course/{${COURSE_ID}}/topic/{${TOPIC_ID}}"
    const val GET_TOPIC_DETAILS = "institute/{${INSTITUTE_ID}}/course/{${COURSE_ID}}/topic/{${TOPIC_ID}}"
    const val CREATE_COURSE_V2 = "institute/{${INSTITUTE_ID}}/v2/course"
    const val GET_COURSE_BY_ID_V2 = "institute/{${INSTITUTE_ID}}/v2/course/{${COURSE_ID}}"
    const val GET_COURSE_BY_NAME = "institute/course/search"

    /**
     * Schedule APIs
     */
    const val GET_SCHEDULE_BY_ID = "schedule/{${SCHEDULE_ID}}"
    const val CREATE_SCHEDULE = "institute/{${INSTITUTE_ID}}/schedule"
    const val INSTITUTE_GET_SCHEDULES = "institute/{${INSTITUTE_ID}}/schedule"
    const val GET_SCHEDULES = "schedule"
    const val GET_ZOOM_HOST_URL_BY_MEETING_ID= "schedule/meeting/{${MEETING_ID}}"

    /**
     * Test APIs
     */
    const val GET_ALL_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/test"
    const val GET_TEST_BY_ID = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/test/{${TEST_ID}}"
    const val ADD_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/test"
    const val UPDATE_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/test/{${TEST_ID}}"
    const val DELETE_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/test/{${TEST_ID}}"
    const val GET_STUDENT_TESTS = "student/{${STUDENT_ID}}/test"
    const val GET_TEACHER_TESTS = "teacher/{${TEACHER_ID}}/test"


    /**
     * Batch Test APIs
     */
    const val GET_ALL_BATCH_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/batchTest"
    const val GET_BATCH_TEST_BY_ID = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/batchTest/{${TEST_ID}}"
    const val ADD_BATCH_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/batchTest"
    const val UPDATE_BATCH_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/batchTest/{${TEST_ID}}"
    const val DELETE_BATCH_TEST = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/batchTest/{${TEST_ID}}"

    /**
     * Payment APIs
     */
    const val GET_PAYMENT_DETAILS_BY_ID = "payment/{${PAYMENT_ID}}"

    /**
     * Notification APIs
     */

    /**
     * Attachment APIs
     */
    const val UPLOAD_ATTACHMENT = "institute/{${INSTITUTE_ID}}/attachment/upload-url"
    const val GET_ATTACHMENT = "institute/{${INSTITUTE_ID}}/attachment/{${ATTACHMENT_ID}}"

    /**
     * Study Material apis
     */
    //columns: id, courseId, list of attachments, name
    // similar to announcements
    const val GET_COURSE_STUDY_MATERIAL = "institute/{${INSTITUTE_ID}}/course/{${COURSE_ID}}/studyMaterial" //missing testing pending
    const val CREATE_COURSE_STUDY_MATERIAL = "institute/{${INSTITUTE_ID}}/batch/{${BATCH_ID}}/studyMaterial"

    /**
     * Announcement apis
     */
    // remove announcement type, add one to many relationship between batch and announcements
    const val INSTITUTE_GET_ANNOUNCEMENTS = "institute/{${INSTITUTE_ID}}/notice" //missing done
    const val CREATE_ANNOUNCEMENT = "institute/{${INSTITUTE_ID}}/notice" //missing done
    const val GET_ANNOUNCEMENT_BY_ID = "institute/{${INSTITUTE_ID}}/notice/{${NOTICE_ID}}" //missing done


    /**
     * Submission APIs
     */

    const val GET_SUBMISSION = "student/{${STUDENT_ID}}/test/{${TEST_ID}}/submission/{${SUBMISSION_ID}}"
    const val ADD_SUBMISSION = "student/{${STUDENT_ID}}/test/{${TEST_ID}}/submission"
    const val GET_ALL_SUBMISSIONS = "institute/{${INSTITUTE_ID}}/test/{${TEST_ID}}/submission"
}
