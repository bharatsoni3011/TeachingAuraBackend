package com.teachingaura.service.impl

import com.teachingaura.api.AttachmentDetails
import com.teachingaura.db.AttachmentRepository
import com.teachingaura.db.model.Attachment
import com.teachingaura.service.AttachmentService
import com.teachingaura.service.InstituteService
import com.teachingaura.util.SignedUrlUtil
import org.springframework.stereotype.Service
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.random.Random

@Service
class AttachmentServiceImpl @Inject constructor(
    private val attachmentRepository: AttachmentRepository,
    private val instituteService: InstituteService,
    private val signedUrlUtil: SignedUrlUtil
) : AttachmentService {

    override suspend fun addAttachment(
        instituteId: String, attachmentDetails: AttachmentDetails
    ): Attachment {
        val institute = instituteService.getInstituteById(instituteId)
        val resourceName = Random.nextLong().toString()
        val id = attachmentDetails.id//UUID.randomUUID().toString()

        val attachment = Attachment(
            id, resourceName, attachmentDetails.name,
            attachmentDetails.attachmentType, institute
        )

        if (isValidURL(attachmentDetails.url)) {
            attachment.url = attachmentDetails.url.toString()
            return attachmentRepository.save(attachment)
        }

        return attachmentRepository.save(attachment).apply {
            this.url = signedUrlUtil.generateSignedUrlToUpload(getObjectName(instituteId, resourceName))
        }
    }

    fun isValidURL(url: String?): Boolean {
        // Regex to check valid URL
        val regex = ("((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)")

        // Compile the ReGex
        val p: Pattern = Pattern.compile(regex)

        // If the string is empty
        // return false
        if (url == null) {
            return false
        }

        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        val m: Matcher = p.matcher(url)

        // Return if the string
        // matched the ReGex
        return m.matches()
    }

    private fun getAttachment(id: String): Attachment {
        return attachmentRepository.findByPid(id) ?: throw Exception("Attachment does not exist")
    }

    override suspend fun getAttachment(instituteId: String, attachmentId: String): Attachment {
        val attachment = getAttachment(attachmentId)
        if (attachment.institute.pid != instituteId) {
            throw Exception("Not allowed to access this resource")
        }
        //return getAttachment(attachmentId)
        //return getAttachment(attachmentId).apply { this.url = signedUrlUtil.generateSignedUrlToDownload(getObjectName(instituteId, attachment.url)) }
        return getAttachment(attachmentId).apply {
            this.url = if (!isValidURL(this.url)) {
                signedUrlUtil.generateSignedUrlToDownload(getObjectName(instituteId, attachment.url))
            } else {
                this.url
            }
        }
    }

    private fun getObjectName(instituteId: String, resourceName: String) = "${instituteId}/${resourceName}"
}
