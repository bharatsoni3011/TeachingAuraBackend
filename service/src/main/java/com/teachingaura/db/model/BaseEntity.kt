package com.teachingaura.db.model

import javax.persistence.*

@MappedSuperclass
open class BaseEntity(

    @Access(AccessType.PROPERTY)
    @Id
    @Column(name = "id", unique = true, nullable = false)
    var pid: String = "",

    @Column(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @Column(name = "modified_at")
    var modifiedAt: Long = System.currentTimeMillis()
) {
    @PreUpdate
    fun updateModifiedAtTime() {
        modifiedAt = System.currentTimeMillis()
    }
}