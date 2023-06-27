package com.teachingaura.api

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AttachmentDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    @field:NotBlank(message = "Name is a required field.")
    val name: String = "",
    val url: String = "",
    @field:NotNull(message = "Attachment Type is a required field.")
    val attachmentType: AttachmentType = AttachmentType.IMAGE
)
