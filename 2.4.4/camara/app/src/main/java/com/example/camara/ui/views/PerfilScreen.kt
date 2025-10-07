package com.example.camara.ui.views

import android.Manifest
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.camara.components.ImagenInteligente
import com.example.camara.viewModels.ProfileViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun PerfilScreen(viewModel: ProfileViewModel) {
    val imageUri by viewModel.imageUri.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope() // Coroutina para lógica asíncrona

    // --- Lanzador para la galería (sin cambios) ---
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            viewModel.onImageChange(uri)
        }
    )

    // --- Lanzadores para la cámara (AQUÍ ESTÁN LOS CAMBIOS) ---
    val file = context.createImageFile()
    val cameraUri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        context.packageName + ".fileprovider", file
    )

    // Lanzador que abre la cámara (el que ya teníamos)
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                viewModel.onImageChange(cameraUri)
            }
        }
    )

    // --- ¡NUEVO! Lanzador para solicitar el permiso de la cámara ---
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Si el permiso es concedido, lanzamos la cámara
                cameraLauncher.launch(cameraUri)
            } else {
                // Opcional: Mostrar un mensaje al usuario de que el permiso es necesario.
                // Por ejemplo, con un Snackbar.
            }
        }
    )
    // --- Interfaz de usuario ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagenInteligente(imageUri = imageUri)
        Spacer(modifier = Modifier.height(32.dp))

        // Botón Galería (sin cambios)
        Button(onClick = { galleryLauncher.launch("image/*") }) {
            Text("Abrir Galería")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- ¡CAMBIO! Botón para la cámara ---
        Button(onClick = {
            // Lógica para verificar y solicitar permiso antes de abrir la cámara
            coroutineScope.launch {
                val permissionStatus = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                if (permissionStatus == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    // Si ya tenemos el permiso, abrimos la cámara directamente
                    cameraLauncher.launch(cameraUri)
                } else {
                    // Si no tenemos el permiso, lo solicitamos
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }
        }) {
            Text("Tomar Foto")
        }
    }
}

// Función de utilidad para crear el archivo temporal para la cámara
private fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}

