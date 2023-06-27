package com.teachingaura.zoom

import com.teachingaura.util.minutes
import com.teachingaura.util.toLocalDateTime
import com.teachingaura.zoom.model.CreateMeetingRequest
import com.teachingaura.zoom.model.MeetingDetails
import com.teachingaura.zoom.model.ZoomUser
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

private val logger = KotlinLogging.logger {}

@Component
class ZoomService(private val zoomClient: ZoomAPI) {

    private var users: List<ZoomUser>

    init {
        runBlocking {
            users = zoomClient.getUsers().users
            logger.info { "Loaded zoom users : $users" }
        }
    }

    suspend fun createMeeting(startTime: Long, endTime: Long): MeetingDetails {
        val meetingStartTime = startTime.toLocalDateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"))
        val meetingDuration = (endTime - startTime) / 1.minutes
        val response = zoomClient.createMeeting(
            users.first().id, CreateMeetingRequest(
                startTime = meetingStartTime,
                durationInMinutes = meetingDuration.toInt()
            )
        )
        return MeetingDetails(response.id, response.start_url, response.join_url, response.host_id)
    }

    suspend fun retriveMeeting(meetingId: Long): MeetingDetails {
        val response = zoomClient.retriveMeeting(meetingId)
        return MeetingDetails(response.id, response.start_url, response.join_url, response.host_id)
    }
}
