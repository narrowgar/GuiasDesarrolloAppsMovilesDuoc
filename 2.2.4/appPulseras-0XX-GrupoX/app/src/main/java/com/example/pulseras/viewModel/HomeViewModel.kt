package com.example.pulseras.viewModel // Or your actual viewModel package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Define HomeUiState here or in a separate file within this package
data class HomeUiState(
    val titulo: String = "Bienvenido (DesdeViewmodel)",
    val subtitulo: String = "Pantalla base con Jetpack Compose y MVVM",
    val contador: Int = 0
)

class HomeViewModel : ViewModel() {

    // Private MutableStateFlow that holds the current UI state
    private val _uiState = MutableStateFlow(HomeUiState())
    // Public immutable StateFlow that Composables can collect from
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // You can initialize state here if needed, e.g., load data
        // For example, setting an initial title from a resource or fetching data
        _uiState.update { currentState ->
            currentState.copy(
                // titulo = getString(R.string.home_title) // If you need context, pass it via constructor or Hilt
            )
        }
    }

    fun incrementarContador() {
        viewModelScope.launch { // Use viewModelScope for coroutines tied to ViewModel lifecycle
            _uiState.update { currentState ->
                currentState.copy(contador = currentState.contador + 1)
            }
        }
    }

    fun reiniciarContador() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(contador = 0)
            }
        }
    }

    // You could add other functions here to update other parts of the UI state
    fun cambiarTitulo(nuevoTitulo: String) {
        _uiState.update { it.copy(titulo = nuevoTitulo) }
    }
}
