package com.example.muskly_trainwithme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


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

    NavigationBar(containerColor = Color(0xFF636989)) { // gris #636989
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
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0xFF333E6B), // azul oscuro
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0xFF333E6B)
                )
            )
        }
    }
}
