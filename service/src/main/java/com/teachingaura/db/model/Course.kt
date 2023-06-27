package com.teachingaura.db.model

import com.teachingaura.api.CourseType
import com.teachingaura.api.CourseDetails
import javax.persistence.*

@Entity
@Table(name = "course")
class Course(
    id: String,

    @Column(name = "name", columnDefinition = "TEXT")
    var name: String,

    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institute_id", referencedColumnName = "id")
    val institute: Institute,

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    var subjects: MutableList<Subject> = mutableListOf(),

    @Column(name = "sub_title", columnDefinition = "TEXT")
    var subTitle: String? = "",

    @Column(name = "url", columnDefinition = "TEXT")
    var url: String? = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "TEXT")
    var type: CourseType? = CourseType.OFFLINE,

    @Column(name = "fee")
    var fee: Long? = 0L,

    @Column(name = "duration_in_months")
    var durationInMonths: Long = 0L,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false

    ) : BaseEntity(id) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Course) return false
        return pid == other.pid
    }

    override fun hashCode(): Int {
        return pid.hashCode()
    }

    fun updateCourse(courseDetails: CourseDetails) {
        this.name = courseDetails.name
        this.description = courseDetails.description
        this.subTitle = courseDetails.subTitle
        this.url = courseDetails.url
        this.type = courseDetails.type
        this.durationInMonths = courseDetails.durationInMonths
        this.fee = courseDetails.fee
    }
}
