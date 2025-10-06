package com.example.pulseras.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pulseras.navigation.AppRoute
import com.example.pulseras.ui.theme.BienvenidaScreen
import com.example.pulseras.ui.theme.HomeScreen // si puedes, mueve HomeScreen a com.example.pulseras.ui
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Bienvenida.route
    ) {
        composable(AppRoute.Bienvenida.route) {
            BienvenidaScreen(
                onIrAHome = { navController.navigate(AppRoute.Home.route) }
            )
        }
        composable(AppRoute.Home.route) {
            HomeScreen(viewModel = viewModel())
        }
        composable(AppRoute.Ajustes.route) {
            AjustesScreen()
        }
    }
}

// Pantalla "dummy" de ejemplo
@Composable
fun AjustesScreen() {
    androidx.compose.material3.Text("Pantalla de Ajustes")
}
