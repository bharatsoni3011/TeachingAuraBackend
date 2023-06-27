package com.teachingaura.roles

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserRecord
import com.teachingaura.filters.FirebaseProvider
import mu.KotlinLogging
import org.springframework.stereotype.Service
import kotlin.math.log

private val logger = KotlinLogging.logger {}

@Service
class RoleService(val firebaseProvider: FirebaseProvider) {

    fun addRoleToUser(id: String, role: Role) {
        val firebaseAuth = firebaseProvider.getFirebase()
        try {
            val user: UserRecord = firebaseAuth.getUser(id)
            val claims = user.customClaims.toMutableMap()
            if (!claims.containsKey(role.toString())) {
                claims[role.toString()] = true
            }
            firebaseAuth.setCustomUserClaims(id, claims)
        } catch (e: FirebaseAuthException) {
            logger.error("Unable to add role : $role to user: $id", e)
        }
    }
}
