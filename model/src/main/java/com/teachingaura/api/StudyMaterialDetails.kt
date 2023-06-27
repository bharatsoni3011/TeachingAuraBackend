package com.teachingaura.api

import javax.validation.constraints.NotBlank

data class StudyMaterialDetails(
    @field:NotBlank(message = "Id is a required field.")
    val id: String = "",
    //@field:NotBlank(message = "Name is a required field.")
    val name: String = "",
    val attachmentDetailsList: List<AttachmentDetails> = listOf(),
    @field:NotBlank(message = "Subject Id is a required field.")
    val subjectId: String = "",
)