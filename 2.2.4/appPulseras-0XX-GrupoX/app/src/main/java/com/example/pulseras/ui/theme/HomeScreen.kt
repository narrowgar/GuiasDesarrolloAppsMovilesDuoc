package com.example.pulseras.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// Remove unnecessary imports if they were only for the local state version
// import androidx.compose.runtime.mutableIntStateOf
// import androidx.compose.runtime.remember
// import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pulseras.R // <-- ajusta el paquete a tu app
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel // Import for viewModel()
import com.example.pulseras.viewModel.HomeViewModel
import com.example.pulseras.viewModel.HomeUiState // Assuming HomeUiState is now in your viewModel package


/**
 * Pantalla base reutilizable (Home) con estructura Scaffold.
 * Incluye: TopAppBar, contenido con Image, Text, Row, Column y Button.
 * Aplica espaciados uniformes, colores y tipografías desde MaterialTheme.
 */

// HomeUiState is now likely defined in your HomeViewModel.kt or a related file.
// If not, ensure it's accessible here. For this example, I'll assume it's moved
// to com.example.pulseras.viewModel.HomeUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(), // Obtain ViewModel instance
    modifier: Modifier = Modifier
) {
    // Recolectar informacion desde el viewModel
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val colorPrimario = MaterialTheme.colorScheme.primary
    // val colorOnPrimary = MaterialTheme.colorScheme.onPrimary // Not used in the current version
    val colorSurface = MaterialTheme.colorScheme.surface

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.titulo,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.reiniciarContador() }) { // Call ViewModel method
                        Icon(
                            painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "Reiniciar contador"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Logo de la app",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = colorSurface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = state.titulo,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = state.subtitulo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Contador: ${state.contador}",
                    style = MaterialTheme.typography.titleMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { viewModel.incrementarContador() }) { // Call ViewModel method
                        Text(text = "Incrementar")
                    }
                    Button(onClick = { viewModel.reiniciarContador() }) { // Call ViewModel method
                        Text(text = "Reiniciar")
                    }
                }
            }

            Surface(
                tonalElevation = 1.dp,
                shadowElevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tip: Esta es una pantalla base reutilizable. " +
                            "Puedes extraer componentes (TopBar, Card, Row de botones) a funciones " +
                            "separadas dentro de ui/ para reutilizarlos en otras pantallas.",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// Remove the old HomeScreen version that managed its own state
/*
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var contador by remember { mutableIntStateOf(0) }
    HomeScreen(
        state = HomeUiState(
            titulo = "Home",
            subtitulo = "Estructura base con Scaffold",
            contador = contador
        ),
        onIncrementar = { contador++ },
        onReiniciar = { contador = 0 },
        modifier = modifier
    )
}
*/

/**
 * Preview de Android Studio para validar rápidamente la UI.
 * This preview will need a HomeViewModel instance.
 * For simplicity in previews, you can pass a mocked state if your ViewModel has complex dependencies.
 * Or, if your ViewModel is simple, you can instantiate it directly.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        // Option 1: Provide a real ViewModel (if it has no complex dependencies for preview)
        // HomeScreen(viewModel = HomeViewModel())

        // Option 2: Use the version of HomeScreen that takes state directly for preview purposes,
        // if you want to keep that for easier previewing without ViewModel instantiation.
        // However, the prompt was to use HomeViewModel.
        // So, let's assume HomeViewModel can be instantiated simply for preview.
        // If not, you might need a fake/mock ViewModel or adjust this preview.

        // For this example, let's assume HomeViewModel can be created directly
        // or you have a way to provide a preview-specific instance.
        val previewViewModel = HomeViewModel() // Or a fake/mock version
        HomeScreen(viewModel = previewViewModel)
    }
}
