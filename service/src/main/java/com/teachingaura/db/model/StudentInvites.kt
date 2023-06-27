package com.teachingaura.db.model

import com.teachingaura.api.InviteType
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "student_invites")
class StudentInvites(

    @EmbeddedId
    val id: StudentInviteKey,

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    val student: Student,

    @ManyToOne
    @MapsId("instituteId")
    @JoinColumn(name = "institute_id")
    val institute: Institute,

    @Enumerated(EnumType.STRING)
    @Column(name = "invite_type")
    var inviteType: InviteType
) {
    fun updateStatus(status: InviteType): StudentInvites {
        if (inviteType == InviteType.INVITED) {
            inviteType = status
        }
        return this
    }
}

@Embeddable
class StudentInviteKey(
    @Column(name = "student_id")
    val studentId: String,

    @Column(name = "insitute_id")
    val instituteId: String
) : Serializable
