package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "batch")
class Batch(

    id: String,

    @Column(name = "name", columnDefinition = "TEXT")
    var name: String,

    @Column(name = "start_date")
    val startDate: Long,

    @Column(name = "active")
    val active: Boolean,

    @Column(name = "image_url", columnDefinition = "TEXT")
    val imageUrl: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "batch_type")
    val batchType: BatchType,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    val course: Course,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute,

    @ManyToMany(mappedBy = "batches")
    var students: MutableSet<Student> = HashSet(),

    @ManyToMany(mappedBy = "batches")
    var teachers: MutableSet<Teacher> = HashSet(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "batch")
    var studyMaterials: MutableList<StudyMaterial> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "batch")
    var tests: MutableList<Test> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "batch")
    var testsV2: MutableList<BatchTest> = mutableListOf(),

    @ManyToMany(mappedBy = "batches")
    var announcements: MutableSet<Announcement> = HashSet(),

    ) : BaseEntity(id)