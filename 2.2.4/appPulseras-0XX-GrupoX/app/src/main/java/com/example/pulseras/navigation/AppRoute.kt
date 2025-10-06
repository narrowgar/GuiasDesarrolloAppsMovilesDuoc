package com.example.pulseras.navigation

sealed class AppRoute(val route: String, val title: String) {
    data object Bienvenida : AppRoute("bienvenida", "Bienvenida")
    data object Home       : AppRoute("home",       "Inicio")
    data object Ajustes    : AppRoute("ajustes",    "Ajustes") // ejemplo extra
    // Agrega más pantallas aquí
    companion object {
        val all = listOf(Bienvenida, Home, Ajustes)
    }
}