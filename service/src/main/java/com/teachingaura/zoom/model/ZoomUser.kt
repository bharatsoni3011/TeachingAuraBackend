package com.teachingaura.zoom.model

import com.google.gson.annotations.SerializedName

data class ZoomUser(
    val id: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)
