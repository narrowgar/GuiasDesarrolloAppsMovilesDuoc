
package com.example.camara.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

/**
 * Componente Composable que muestra una imagen de perfil de forma circular.
 * Si la URI es nula, muestra un ícono de perfil por defecto.
 *
 * @param imageUri La URI de la imagen a mostrar. Puede ser nula.
 */
@Composable
fun ImagenInteligente(imageUri: Uri?) {
    val imageSize = 150.dp

    // Usamos Card para darle una elevación y forma circular fácilmente
    Card(
        modifier = Modifier.size(imageSize),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (imageUri != null) {
            // Si hay una imagen, la mostramos usando Coil
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape), // Recorta la imagen en forma de círculo
                contentScale = ContentScale.Crop // Asegura que la imagen llene el círculo
            )
        } else {
            // Si no hay imagen, mostramos un ícono por defecto
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "Ícono de perfil por defecto",
                modifier = Modifier.size(imageSize)
            )
        }
    }
}
    