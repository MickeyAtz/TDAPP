package com.mky.tdapp.data.repository

import com.mky.tdapp.data.model.LoginRequest
import com.mky.tdapp.data.model.LoginResponse
import com.mky.tdapp.data.remote.AuthApi

class AuthRepository(
    private val api: AuthApi
){
    suspend fun login(
        correo: String,
        password: String
    ) : LoginResponse {
        val response = api.login(LoginRequest(email = correo, password = password))

        if(!response.isSuccessful) {
            throw Exception("Error en login")
        }

        return response.body() ?: throw Exception("Respuesta vacia")
    }
}