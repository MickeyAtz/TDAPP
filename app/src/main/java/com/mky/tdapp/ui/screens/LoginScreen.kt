package com.mky.tdapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.mky.tdapp.ui.components.AppTextField

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(18.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Login",
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

        Button( onClick = {
            Toast.makeText(
                context,
                "Email: $email  | Password: $password",
                Toast.LENGTH_SHORT
            ).show()
        }) {
            Text("Iniciar Sesión")
        }
    }
}