package com.mky.tdapp.ui.navigation

sealed class Screen(val route: String) {
    object Materias : Screen("materias")
    object Grupos : Screen("Grupos")
}