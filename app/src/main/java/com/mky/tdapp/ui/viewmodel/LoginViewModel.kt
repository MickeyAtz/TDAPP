package com.mky.tdapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mky.tdapp.data.model.LoginResponse
import com.mky.tdapp.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    fun login(
        correo:String,
        password: String,
        onSuccess: (LoginResponse) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = repository.login(correo, password)
                onSuccess(response)
            } catch (e: Exception) {
                error = e.message
            }
            isLoading = false
        }
    }
}