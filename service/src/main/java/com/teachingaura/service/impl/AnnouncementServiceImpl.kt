package com.teachingaura.service.impl

import com.teachingaura.db.AnnouncementsRepository
import com.teachingaura.db.model.Announcement
import com.teachingaura.exception.AnnouncementDoesNotExistsException
import com.teachingaura.exception.InstituteDoesNotExistsException
import com.teachingaura.service.AnnouncementService
import javax.inject.Inject

class AnnouncementServiceImpl @Inject constructor(
    private val announcementsRepository: AnnouncementsRepository
) : AnnouncementService {
    override suspend fun getAnnouncementById(id: String): Announcement {
        return announcementsRepository.findByPid(id) ?: throw AnnouncementDoesNotExistsException()
    }
}