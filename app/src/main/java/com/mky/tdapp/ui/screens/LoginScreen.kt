package com.mky.tdapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.mky.tdapp.ui.components.AppTextField
import com.mky.tdapp.ui.components.AppButton
import com.mky.tdapp.R
import com.mky.tdapp.data.remote.AuthApi
import com.mky.tdapp.data.remote.RetrofitInstance
import com.mky.tdapp.data.repository.AuthRepository
import com.mky.tdapp.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSucess: () -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}

    val api = RetrofitInstance.retrofit.create(AuthApi::class.java)
    val repository = AuthRepository(api)
    val viewModel = remember { LoginViewModel(repository) }

    val logoRes = if(isSystemInDarkTheme()) {
        R.drawable.logo_darkmode
    } else {
        R.drawable.logo
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = logoRes),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(){
                Text(
                    text = "TEC Detective",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Bienvenido",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))


        Text(text = "Inicio de Sesión",
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = email,
            onValueChange = {email = it},
            label = "Email",
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            value = password,
            onValueChange = {password = it},
            label = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppButton(
            text = "Iniciar Sesión",
            onClick = {
                viewModel.login(email, password) { response ->
                    RetrofitInstance.token = response.token

                    Toast.makeText(context, "Login exitoso",  Toast.LENGTH_SHORT).show()
                    onLoginSucess()
                }
            }
        )

        viewModel.error?.let {
            Text("Error: $it")
        }
    }
}