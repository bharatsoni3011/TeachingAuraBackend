package com.teachingaura.spring.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security")
data class SecurityProperties(
//    val cookieProps: CookieProperties? = null,
//    val firebaseProps: FirebaseProperties? = null,
    val allowCredentials: Boolean = false,
    val allowedOrigins: List<String> = listOf(),
    val allowedHeaders: List<String> = listOf(),
    val exposedHeaders: List<String> = listOf(),
    val allowedMethods: List<String> = listOf(),
    val allowedPublicApis: List<String> = listOf(),
    var superAdmins: List<String> = listOf("test2@springbackend.com"),
    var validApplicationRoles: List<String> = listOf()
)
