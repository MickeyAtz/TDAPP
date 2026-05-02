package com.mky.tdapp.data.remote

import com.mky.tdapp.data.model.LoginRequest
import com.mky.tdapp.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ) : Response<LoginResponse>
}