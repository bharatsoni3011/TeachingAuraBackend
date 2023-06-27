package com.teachingaura

import com.teachingaura.api.FirebaseApi
import com.teachingaura.api.InstituteService
import com.teachingaura.api.StudentService
import com.teachingaura.api.TeacherService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object TeachingAuraClient {

//    private const val BASE_URL = "http://localhost:8080/api/"
    private const val BASE_URL = "https://teachingaura-dk6fq7sbfa-uc.a.run.app/api/"

    private val client = getClient()
    private lateinit var authenticatedClient: Retrofit

    fun setToken(token: String) {
        authenticatedClient = getAuthenticatedClient(token)
    }

    fun getFirebaseService(): FirebaseApi {
        return client.create(FirebaseApi::class.java)
    }

    fun getStudentService(): StudentService {
        return authenticatedClient.create(StudentService::class.java)
    }

    fun getTeacherService(): TeacherService {
        return authenticatedClient.create(TeacherService::class.java)
    }

    private fun getClient(): Retrofit {
        val gsonConverterFactory = GsonConverterFactory.create()
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BASE_URL)
            .build()
    }

    private fun getAuthenticatedClient(token: String): Retrofit {
        val gsonConverterFactory = GsonConverterFactory.create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(getOkHttpClient(token))
            .build()
    }

    private fun getOkHttpClient(token: String): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(FirebaseTokenInterceptor(token))
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    fun getInstituteService(): InstituteService {
        return authenticatedClient.create(InstituteService::class.java)
    }
}