package com.teachingaura.db.model

import javax.persistence.*

@Entity
@Table(name = "institute")
class Institute(

    id: String,

    @Column(name = "name", columnDefinition = "TEXT")
    var name: String,

    @Column(name = "logo", columnDefinition = "TEXT")
    var logo: String,

    @Column(name = "email", columnDefinition = "TEXT")
    var email: String,

    @Column(name = "contact_number", columnDefinition = "TEXT")
    var contactNumber: String,

    @Column(name = "about_us", columnDefinition = "TEXT")
    var aboutUs: String?,

    @Column(name = "owner_name", columnDefinition = "TEXT")
    var ownerName: String?,

    @Column(name = "owner_email", columnDefinition = "TEXT")
    var ownerEmail: String?,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val schedules: List<Schedule> = listOf(),

    @OneToMany
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val batches: List<Batch> = listOf(),

    @ManyToMany(mappedBy = "institutes")
    val teachers: Set<Teacher> = HashSet(),

    @ManyToMany(mappedBy = "institutes")
    val students: Set<Student> = HashSet(),

    @OneToMany(mappedBy = "institute")
    val courses: List<Course> = listOf(),

    @OneToMany(mappedBy = "institute")
    val studentInvites: MutableSet<StudentInvites> = mutableSetOf(),

    @OneToMany(mappedBy = "institute")
    val teacherInvites: MutableSet<TeacherInvites> = mutableSetOf(),

) : BaseEntity(id)