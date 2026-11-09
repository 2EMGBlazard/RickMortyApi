package com.ucaldas.rickmortyapi.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val sharedViewModel: RickmortyViewModel = viewModel()

            NavHost(navController = navController, startDestination = "lista") {
                composable("lista") {
                    RickmortyScreen(
                        viewModel = sharedViewModel,
                        onPersonajeClick = { id ->
                            navController.navigate("detalle/$id")
                        }
                    )
                }
                composable(
                    route = "detalle/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: 0
                    DetallePersonajeScreen(personajeId = id, viewModel = sharedViewModel)
                }
            }
        }
    }
}