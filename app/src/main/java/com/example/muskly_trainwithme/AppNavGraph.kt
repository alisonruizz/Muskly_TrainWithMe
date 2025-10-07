package com.example.muskly_trainwithme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muskly_trainwithme.trainscreen.trainScreen

// === RUTAS DE NAVEGACIÓN ===
enum class NavRoutes(val route: String) {
    Home("home"),
    Train("train"),
    Tips("tips"),
    Goals("goals"),
    Shop("shop")
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBarWithNavController(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Home.route, // Ruta inicial por defecto
            modifier = modifier.padding(paddingValues)
        ) {
            composable(NavRoutes.Home.route) {
                TrainStartScreen()
            }
            composable(NavRoutes.Train.route) {
                trainScreen()
            }
            composable(NavRoutes.Tips.route) {
                TipsScreen()
            }
            composable(NavRoutes.Goals.route) {
                GoalsScreen(onRewardEarned = {})
            }
            composable(NavRoutes.Shop.route) {
                ShopScreen()
            }
        }
    }
}
