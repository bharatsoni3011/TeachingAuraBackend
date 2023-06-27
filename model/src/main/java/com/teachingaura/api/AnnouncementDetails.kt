package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class AnnouncementDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotBlank(message = "Content is a required field.")
    val content: String = "",
    val attachmentIds: List<String> = listOf(),
    val batches: List<String> = listOf(),
    val instituteId: String = "",
    val name: String = "",
)
