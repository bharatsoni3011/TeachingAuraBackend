package com.teachingaura.util

import com.teachingaura.api.*
import com.teachingaura.controller.TestController
import com.teachingaura.db.model.*
import com.teachingaura.service.StudentControllerTest
import com.teachingaura.service.TestControllerTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TestUtil {

    @Autowired
    private lateinit var instituteUtil: InstituteUtil

    fun getTestDetails(): TestDetails {
        return TestDetails(
            TestControllerTest.TEST_ID,
            TestControllerTest.TEST_NAME,
            TestControllerTest.TEST_DESCRIPTION,
            TestControllerTest.START_TIME,
            TestControllerTest.END_TIME,
            TestControllerTest.TEST_ID,
            listOf(getTestAttachmentDetails()),
            listOf()
        )
    }

    fun getTestDetailsWithoutTestAttachment(): TestDetails {
        return TestDetails(TestControllerTest.TEST_ID, TestControllerTest.TEST_NAME, TestControllerTest.TEST_DESCRIPTION, TestControllerTest.START_TIME, TestControllerTest.END_TIME, TestControllerTest.TEST_ID)
    }

    fun getTestAttachmentDetails(): TestAttachmentDetails {
        return TestAttachmentDetails(TestControllerTest.TEST_ID)
    }

    fun getAttachmentDetails(): AttachmentDetails {
        return AttachmentDetails(TestControllerTest.TEST_ID, TestControllerTest.TEST_NAME, TestControllerTest.TEST_URL)
    }

    fun getSubmissionDetails(): SubmissionDetails {
        return SubmissionDetails(
            TestControllerTest.TEST_ID, TestControllerTest.START_TIME, TestControllerTest.TEST_ID,
            TestControllerTest.TEST_NAME, TestControllerTest.TEST_MARKS, listOf(getTestAttachmentDetails())
        )
    }

}