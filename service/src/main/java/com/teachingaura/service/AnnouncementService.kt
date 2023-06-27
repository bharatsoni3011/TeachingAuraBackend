package com.teachingaura.service

import com.teachingaura.db.model.Announcement
import com.teachingaura.db.model.Institute

interface AnnouncementService {

    suspend fun getAnnouncementById(id: String): Announcement
}