package com.example.muskly_trainwithme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muskly_trainwithme.data.MascotaDatabase
import com.example.muskly_trainwithme.goalsscreen.GoalsScreen
import com.example.muskly_trainwithme.repository.MascotaRepository
import com.example.muskly_trainwithme.shopscreen.ShopScreen
import com.example.muskly_trainwithme.trainscreen.trainScreen
import com.example.muskly_trainwithme.ui.tips.TipsScreen
import com.example.muskly_trainwithme.viewmodel.MascotaViewModel
import com.example.muskly_trainwithme.viewmodel.MascotaViewModelFactory

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

                    val context = LocalContext.current
                    val database = MascotaDatabase.getDatabase(context)
                    val repository = MascotaRepository(database.mascotaDao())
                    val mascotaViewModel: MascotaViewModel = viewModel(
                        factory = MascotaViewModelFactory(repository)
                    )
                GoalsScreen(
                    mascotaViewModel = mascotaViewModel,
                    onRewardEarned = { reward ->
                        mascotaViewModel.actualizarMonedasYExp(experiencia = 1, monedas = reward)
                    }
                )



            }
            composable(NavRoutes.Shop.route) {
                ShopScreen()
            }
        }
    }
}
