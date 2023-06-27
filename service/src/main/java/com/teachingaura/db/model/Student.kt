package com.teachingaura.db.model

import com.teachingaura.api.MaxEducation
import javax.persistence.*

@Entity
@Table(name = "student")
class Student(
    id: String,
    @Column(name = "name", columnDefinition = "TEXT")
    var name: String,

    @Column(name = "email", columnDefinition = "TEXT")
    var email: String,

    @Column(name = "contact_number", columnDefinition = "TEXT")
    var contactNumber: String,

    @Column(name = "DOB")
    var dateOfBirth: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "max_education")
    var maxEducation: MaxEducation,

    //owner of relationship
    @ManyToMany
    @JoinTable(
        name = "batch_enrollment",
        joinColumns = [JoinColumn(name = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "batch_id")]
    )
    var batches: MutableSet<Batch> = mutableSetOf(),

    //owner of relationship
    @ManyToMany
    @JoinTable(
        name = "student_institutes",
        joinColumns = [JoinColumn(name = "student_id")],
        inverseJoinColumns = [JoinColumn(name = "institute_id")]
    )
    var institutes: MutableSet<Institute> = mutableSetOf(),

    @OneToMany(mappedBy = "student")
    var invites: MutableSet<StudentInvites> = mutableSetOf(),

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
        batches -= batch
    }

    fun removeAllBatches() {
        batches = mutableSetOf()
    }

    fun removeAllInstitutes() {
        institutes = mutableSetOf()
    }

    fun removeAllInvites() {
        invites = mutableSetOf()
    }
}
