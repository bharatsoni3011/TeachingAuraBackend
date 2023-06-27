package com.teachingaura.db.model

import com.teachingaura.api.AttachmentType
import com.teachingaura.api.TestAttachmentType
import javax.persistence.*

@Entity
@Table(name = "test_attachment")
class TestAttachment(

    id: String,

    @OneToMany(fetch = FetchType.EAGER)
    val attachments: List<Attachment> = listOf(),

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_attachment")
    val typeOfAttachment: TestAttachmentType,

    ) : BaseEntity(id)