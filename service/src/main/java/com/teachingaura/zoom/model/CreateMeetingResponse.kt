package com.teachingaura.zoom.model


data class CreateMeetingResponse(
    val id: String,
    val assistant_id: String,
    val host_email: String,
    val registration_url: String,
    val topic:String,
    val type:Int,
    val pre_schedule:Boolean,
    val start_time:String,
    val duration:Int,
    val timezone:String,
    val createdAt:String,
    val agenda:String,
    val start_url:String,
    val join_url:String,
    val password:String,
    val pmi:Int,
    val trackingFields: List<TrackingField> = listOf(),
    val settings: Settings,
    val recurrence: Recurrence,
    val host_id: String
)

