package com.teachingaura.db

import com.teachingaura.db.model.Test
import com.teachingaura.db.model.TestAttachment
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TestAttachmentRepository : CrudRepository<TestAttachment, String> {

    fun findByPid(id: String): TestAttachmentRepository?
}