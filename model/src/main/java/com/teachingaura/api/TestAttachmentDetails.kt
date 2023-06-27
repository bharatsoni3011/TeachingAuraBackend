package com.teachingaura.api

data class TestAttachmentDetails(
    val id: String = "",
    val attachments: List<AttachmentDetails> = listOf(),
    val typeOfTestAttachment: TestAttachmentType = TestAttachmentType.SUBMISSION,
)
