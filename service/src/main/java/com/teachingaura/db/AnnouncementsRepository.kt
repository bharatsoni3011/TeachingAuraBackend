package com.teachingaura.db

import com.teachingaura.db.model.Announcement
import com.teachingaura.db.model.Institute
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface AnnouncementsRepository : CrudRepository<Announcement, String> {

    @Query("select a from Announcement a where a.institute in ?1")
    fun findByInstituteIn(instituteId: List<Institute?>?): List<Announcement>

    fun findByInstitutePid(instituteId: String): List<Announcement>

    fun findByPid(id: String): Announcement?

    fun deleteByInstitutePid(instituteId: String)

}