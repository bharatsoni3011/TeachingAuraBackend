package com.teachingaura.api


data class NoticeDetails(
    val id: String = "",
    val content: String = "",
    val attachmentDetailsList: List<AttachmentDetails> = listOf(),
    // batch id
    val batches: List<BatchDetails> = listOf(),
    val createdAt: Long = 0L
)
