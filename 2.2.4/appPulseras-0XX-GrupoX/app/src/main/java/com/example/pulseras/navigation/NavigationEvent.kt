package com.example.pulseras.navigation

sealed class NavigationEvent {
    data class To(val route: AppRoute) : NavigationEvent()
    data object Back : NavigationEvent()
    data class OpenDrawer(val open: Boolean) : NavigationEvent()
}