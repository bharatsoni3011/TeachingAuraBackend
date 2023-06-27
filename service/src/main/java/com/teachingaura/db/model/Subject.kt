package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "subject")
class Subject(

    id:String,

    @Column(name = "name", nullable = false, columnDefinition = "TEXT")
    val name: String = "",

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    val topics: List<Topic> = listOf(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    val course: Course,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    val studyMaterials: List<StudyMaterial> = listOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    val schedules: List<StudyMaterial> = listOf()

) : BaseEntity(id)