package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "teacher")
class Teacher(

    id: String,

    @Column(name = "name", columnDefinition = "TEXT")
    var name: String = "",

    @Column(name = "contact_number", columnDefinition = "TEXT")
    var contactNumber: String = "",

    @Column(name = "email", columnDefinition = "TEXT")
    var email: String = "",

    @Column(name = "subject", columnDefinition = "TEXT")
    var subject: String,

    //owner of relationship
    @ManyToMany
    @JoinTable(
        name = "teacher_batch",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "batch_id")]
    )
    var batches: MutableSet<Batch> = mutableSetOf(),

    @ManyToMany
    @JoinTable(
        name = "teacher_institutes",
        joinColumns = [JoinColumn(name = "teacher_id")],
        inverseJoinColumns = [JoinColumn(name = "institute_id")]
    )
    val institutes: MutableList<Institute> = mutableListOf(),

    @OneToMany(mappedBy = "teacher")
    val invites: MutableSet<TeacherInvites> = mutableSetOf()
) : BaseEntity(id) {
    fun addInstitute(institute: Institute) {
        institutes += institute
    }

    fun removeInstitute(institute: Institute) {
        batches = batches.filterNot { it.institute.pid == institute.pid }.toMutableSet()
        institutes -= institute
    }

    fun addBatch(batch: Batch) {
        batches += batch
    }

    fun removeBatch(batch: Batch) {
        batches = batches.filterNot { it.pid == batch.pid }.toMutableSet()
        batches -= batch
    }
}
