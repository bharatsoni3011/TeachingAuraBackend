package com.teachingaura.api

import com.teachingaura.api.model.TokenRequest
import com.teachingaura.api.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FirebaseApi {

    @POST("https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyPassword?key=AIzaSyBLojAFfFqSyxUEhkVs9tE0XOa9bzEJuoI")
    suspend fun getToken(@Body request: TokenRequest): TokenResponse

}