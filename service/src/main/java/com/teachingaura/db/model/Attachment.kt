package com.teachingaura.db.model

import com.teachingaura.api.AttachmentType
import javax.persistence.*

@Entity
@Table(name = "attachment")
class Attachment(
    id: String,
    @Column(name = "url", columnDefinition = "TEXT")
    var url: String,

    @Column(name = "name", columnDefinition = "TEXT")
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "attachment_type")
    val attachmentType: AttachmentType,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute
) : BaseEntity(id)