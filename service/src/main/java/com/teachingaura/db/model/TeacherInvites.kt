package com.teachingaura.db.model

import com.teachingaura.api.InviteType
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "teacher_invites")
class TeacherInvites(

    @EmbeddedId
    var id: TeacherInviteKey,

    @ManyToOne
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    val teacher: Teacher,

    @ManyToOne
    @MapsId("instituteId")
    @JoinColumn(name = "institute_id")
    val institute: Institute,

    @Enumerated(EnumType.STRING)
    @Column(name = "invite_type")
    var inviteType: InviteType
) {
    fun updateStatus(status: InviteType): TeacherInvites {
        if (inviteType == InviteType.INVITED) {
            inviteType = status
        }
        return this
    }
}

@Embeddable
class TeacherInviteKey(
    @Column(name = "teacher_id")
    val teacherId: String,

    @Column(name = "insitute_id")
    val instituteId: String
) : Serializable
