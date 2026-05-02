package com.mky.tdapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mky.tdapp.ui.screens.LoginScreen
import com.mky.tdapp.ui.screens.MateriasScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {

    val navController = rememberNavController()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            if(currentRoute != "login"){
                TopAppBar(
                    title = {
                        Text("TEC Detective")
                    }
                )
            }
        },

        bottomBar = {
            if (currentRoute != "login") {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("login") {
                LoginScreen(
                    onLoginSucess = {
                        navController.navigate("materias") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            composable("materias") {
                MateriasScreen()
            }
        }
    }
}