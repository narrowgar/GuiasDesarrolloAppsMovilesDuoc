package com.example.pulseras.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel


import com.example.pulseras.viewModel.BienvenidaViewModel

// 1) UI "pura": no usa ViewModel. Aquí sí ponemos @Preview.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BienvenidaContent(
    mensaje: String,
    onIrAHome: () -> Unit = {}
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(mensaje, fontSize = 5.em)
            Button(onClick = onIrAHome, modifier = Modifier.padding(top = 16.dp)) {
                Text("Ir a Home")
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BienvenidaContentPreview() {
    BienvenidaContent(mensaje = "Bienvenido desde Preview (sin VM)")
}

// 2) UI con ViewModel: SIN @Preview
@Composable
fun BienvenidaScreen(
    viewModel: BienvenidaViewModel = viewModel(),
    onIrAHome: () -> Unit = {}
) {
    BienvenidaContent(
        mensaje = viewModel.mensajeSaludo,
        onIrAHome = onIrAHome
    )
}