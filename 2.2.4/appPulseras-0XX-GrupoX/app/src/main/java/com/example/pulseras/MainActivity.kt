
package com.example.pulseras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pulseras.ui.theme.AppNavigation
import com.example.pulseras.ui.theme.PulserasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PulserasTheme {
                AppNavigation()
            }
        }
    }
}