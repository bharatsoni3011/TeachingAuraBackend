package com.teachingaura

import okhttp3.Interceptor

class FirebaseTokenInterceptor(private val token: String)  : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()

        request = request.newBuilder()
            .header("Authorization", "Bearer $token").build()
        return chain.proceed(request)
    }
}
