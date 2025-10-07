package com.example.camara.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    // StateFlow privado y mutable para uso interno del ViewModel.
    // Guardará la Uri de la imagen. Puede ser nulo si no hay imagen.
    private val _imageUri = MutableStateFlow<Uri?>(null)

    // StateFlow público e inmutable para que la UI lo observe.
    // Expone el _imageUri de forma segura (solo lectura).
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    /**
     * Actualiza el estado con la nueva Uri proveniente de la galería o la cámara.
     * @param uri La Uri de la imagen seleccionada o capturada.
     */
    fun onImageChange(uri: Uri?) {
        _imageUri.update { uri }
    }
}
