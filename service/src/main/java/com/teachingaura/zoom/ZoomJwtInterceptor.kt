package com.teachingaura.zoom

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import okhttp3.Interceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class ZoomJwtInterceptor : Interceptor {

    @Value("\${zoom.key}")
    private lateinit var apiKey: String

    @Value("\${zoom.secret}")
    private lateinit var apiSecret: String

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization", "Bearer ${getJwtToken()}").build()
        return chain.proceed(request)
    }

    fun getJwtToken(): String {
        val algorithm = Algorithm.HMAC256(apiSecret)
        return JWT.create()
            .withIssuer(apiKey)
            .withExpiresAt(Date(System.currentTimeMillis() + 60 * 60 * 1000))
            .sign(algorithm);
    }
}
