package com.teachingaura.filters

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.spring.core.GcpProjectIdProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import org.springframework.stereotype.Component

@Component
class FirebaseProvider(gcpProjectIdProvider: GcpProjectIdProvider) {

    init {
        val options = FirebaseOptions.Builder()
            .setProjectId(gcpProjectIdProvider.projectId)
            .setCredentials(GoogleCredentials.getApplicationDefault())
            .build()
        if(FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }

    fun getFirebase(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}
