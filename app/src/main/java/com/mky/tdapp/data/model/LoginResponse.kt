package com.mky.tdapp.data.model

data class LoginResponse(
    val token: String,
    val user: User
)