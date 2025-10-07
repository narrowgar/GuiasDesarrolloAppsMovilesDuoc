package com.example.camara

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.camara.ui.theme.CamaraTheme
import com.example.camara.ui.views.PerfilScreen
import com.example.camara.viewModels.ProfileViewModel

class MainActivity : ComponentActivity() {
    // Instancia del ViewModel asociada al ciclo de vida de la Activity

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamaraTheme { // O el nombre de tu tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pasamos la instancia del ViewModel a nuestra pantalla
                    PerfilScreen(viewModel = profileViewModel)
                }
            }
        }
    }
}


