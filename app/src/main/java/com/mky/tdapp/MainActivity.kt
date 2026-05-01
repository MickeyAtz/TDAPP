package com.mky.tdapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.mky.tdapp.ui.theme.TDAPPTheme

import com.mky.tdapp.ui.screens.LoginScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TDAPPTheme {
                LoginScreen()
            }
        }
    }
}



