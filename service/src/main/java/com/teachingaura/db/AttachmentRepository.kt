package com.teachingaura.db

import com.teachingaura.db.model.Attachment
import org.springframework.data.repository.CrudRepository

interface AttachmentRepository  : CrudRepository<Attachment, String> {

    fun findByPid(id: String): Attachment?
}
