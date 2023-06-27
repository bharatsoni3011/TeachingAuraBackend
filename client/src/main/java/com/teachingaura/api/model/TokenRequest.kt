package com.teachingaura.api.model

data class TokenRequest(val email : String, val password: String, val returnSecureToken: Boolean)
