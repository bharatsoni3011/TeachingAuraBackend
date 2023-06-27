package com.teachingaura.service

import com.teachingaura.api.AttachmentDetails
import com.teachingaura.db.model.Attachment

interface AttachmentService {

    suspend fun addAttachment(instituteId: String, attachmentDetails: AttachmentDetails): Attachment

    suspend fun getAttachment(instituteId: String, attachmentId: String): Attachment
}
