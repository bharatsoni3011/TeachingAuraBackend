package com.teachingaura.zoom

import com.teachingaura.zoom.model.CreateMeetingRequest
import com.teachingaura.zoom.model.CreateMeetingResponse
import com.teachingaura.zoom.model.GetUsersResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ZoomAPI {

    // support pagination in this api in future
    @GET(GET_USERS)
    suspend fun getUsers(): GetUsersResponse

    @POST(CREATE_MEETING)
    suspend fun createMeeting(@Path("userId") userId: String, @Body request: CreateMeetingRequest): CreateMeetingResponse

    companion object {
        private const val GET_USERS = "users"
        private const val CREATE_MEETING = "users/{userId}/meetings"
        private const val RETRIVE_MEETING = "meetings/{meetingId}"
    }

    @GET(RETRIVE_MEETING)
    suspend fun retriveMeeting(@Path("meetingId") meetingId: Long): CreateMeetingResponse
}
