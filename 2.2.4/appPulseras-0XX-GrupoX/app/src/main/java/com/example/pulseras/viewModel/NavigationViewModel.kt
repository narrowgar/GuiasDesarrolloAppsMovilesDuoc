package com.example.pulseras.viewModel

import androidx.lifecycle.ViewModel
import com.example.pulseras.navigation.AppRoute
import com.example.pulseras.navigation.NavigationEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {

    // estado simple para el Drawer (abierto/cerrado)
    private val _isDrawerOpen = MutableStateFlow(false)
    val isDrawerOpen = _isDrawerOpen.asStateFlow()

    fun onEvent(event: NavigationEvent): AppRoute? {
        return when (event) {
            is NavigationEvent.OpenDrawer -> {
                _isDrawerOpen.value = event.open
                null
            }
            is NavigationEvent.To -> {
                _isDrawerOpen.value = false
                event.route
            }
            NavigationEvent.Back -> null
        }
    }
}
