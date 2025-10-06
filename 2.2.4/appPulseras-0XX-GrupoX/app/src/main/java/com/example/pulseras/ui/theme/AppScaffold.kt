package com.example.pulseras.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pulseras.navigation.AppRoute
import com.example.pulseras.navigation.NavigationEvent
import com.example.pulseras.viewModel.NavigationViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pulseras.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navVM: NavigationViewModel = viewModel()
    val isDrawerOpen by navVM.isDrawerOpen.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 1) VM -> UI: aplica el estado del VM al Drawer
    LaunchedEffect(isDrawerOpen) {
        if (isDrawerOpen && !drawerState.isOpen) {
            scope.launch { drawerState.open() }
        } else if (!isDrawerOpen && !drawerState.isClosed) {
            scope.launch { drawerState.close() }
        }
    }

    // 2) UI -> VM: si el usuario abre/cierra con gestos/scrim, refleja en el VM
    LaunchedEffect(drawerState) {
        snapshotFlow { drawerState.currentValue }
            .collect { value ->
                val open = value == DrawerValue.Open
                navVM.onEvent(NavigationEvent.OpenDrawer(open))
            }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))

                AppRoute.all.forEach { route ->
                    NavigationDrawerItem(
                        label = { Text(route.title) },
                        selected = false,
                        onClick = {
                            // Cierra el drawer en VM y navega
                            navVM.onEvent(NavigationEvent.OpenDrawer(false))
                            navController.navigate(route.route) {
                                popUpTo(AppRoute.Bienvenida.route) { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Pulseras") },
                    navigationIcon = {
                        IconButton(onClick = {
                            navVM.onEvent(NavigationEvent.OpenDrawer(true))
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "Abrir menú"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(Modifier.fillMaxSize().padding(innerPadding)) {
                AppNavGraph(navController = navController)
            }
        }
    }
}
