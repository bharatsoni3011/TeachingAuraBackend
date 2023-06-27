package com.teachingaura.zoom.model

import com.google.gson.annotations.SerializedName

data class CreateMeetingRequest(
    val topic: String = "Test Meeting",
    val type: Int = 2,
    @SerializedName("pre_schedule") val preSchedule: Boolean = false,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("duration") val durationInMinutes: Int = 60,
    @SerializedName("schedule_for") val scheduleFor: String? = null,
    val timezone: String = "UTC",
    val password: String? = null,
    @SerializedName("default_password") val defaultPassword: Boolean = false,
    val agenda: String = "",
    @SerializedName("tracking_fields") val trackingFields: List<TrackingField> = listOf(),
    val recurrence: Recurrence? = null,
    val settings: Settings? = null,
    @SerializedName("template_id") val templateId: String? = null
)

// add more based on requirements
data class Settings(
    val host_video: Boolean,
    val participant_video: Boolean,
    val cn_meeting: Boolean,
    val in_meeting: Boolean,
    val join_before_host: Boolean
)

data class Recurrence(
    val type: Int,
    @SerializedName("repeat_interval") val repeatInterval: Int,
    val weekly_days: String,
    val monthly_day: Int,
    val monthly_week: Int,
    val monthly_week_day: Int,
    val end_times: Int,
    val end_date_time: String
)

data class TrackingField(val field: String, val value: String)
