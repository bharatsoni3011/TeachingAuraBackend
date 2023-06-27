package com.teachingaura.controller

import com.teachingaura.api.API.GET_ATTACHMENT
import com.teachingaura.api.API.UPLOAD_ATTACHMENT
import com.teachingaura.api.AttachmentDetails
import com.teachingaura.service.AttachmentService
import com.teachingaura.toApi
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AttachmentController @Inject constructor(private val attachmentService: AttachmentService) : BaseController() {

    @PostMapping(UPLOAD_ATTACHMENT)
    suspend fun generateSignedUrlToUpload(
        @PathVariable instituteId: String,
        @Valid  @RequestBody attachmentDetails: AttachmentDetails
    ): AttachmentDetails {
        return attachmentService.addAttachment(instituteId, attachmentDetails).toApi()
    }

    @GetMapping(GET_ATTACHMENT)
    suspend fun getAttachmentById(
        @PathVariable instituteId: String,
        @PathVariable attachmentId: String
    ): AttachmentDetails {
        return attachmentService.getAttachment(instituteId, attachmentId).toApi()
    }
}
