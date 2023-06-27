package com.teachingaura.filters

import com.google.firebase.auth.FirebaseToken

data class Credentials(val type: CredentialType, val decodedToken: FirebaseToken, val idToken: String)

enum class CredentialType {
    ID_TOKEN
}
