package com.teachingaura.zoom

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class ZoomConfig {

    @Bean
    fun getZoomClient(zoomHttpClient: OkHttpClient): ZoomAPI {
        val gsonConverterFactory = GsonConverterFactory.create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.zoom.us/v2/")
            .addConverterFactory(gsonConverterFactory)
            .client(zoomHttpClient)
            .build()
        return retrofit.create(ZoomAPI::class.java)
    }

    @Bean
    fun zoomHttpClient(zoomJwtInterceptor: ZoomJwtInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(zoomJwtInterceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()
    }
}
