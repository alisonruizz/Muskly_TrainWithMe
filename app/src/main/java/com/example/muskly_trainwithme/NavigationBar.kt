package com.example.muskly_trainwithme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController


@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val items = listOf("Home", "Train", "Tips", "Goals", "Shop")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Person,
        Icons.Default.List,
        Icons.Default.Star,
        Icons.Default.ShoppingCart
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) { // gris #636989
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index] as ImageVector,
                        contentDescription = label
                    )
                },
                label = { Text(label) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedIconColor =  MaterialTheme.colorScheme.secondary, // azul oscuro
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

@Composable
fun BottomNavigationBarWithNavController(navController: NavHostController) {
    val items = listOf(
        NavRoutes.Tips to Icons.Default.List,
        NavRoutes.Goals to Icons.Default.Star
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { (route, icon) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = route.name) },
                label = { Text(route.name) },
                selected = false, // aquí podrías usar currentDestination si quieres resaltar
                onClick = {
                    navController.navigate(route.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

