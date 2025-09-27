package com.example.muskly_trainwithme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme

@Composable
fun StoreScreen() {
    var selectedTab by remember { mutableStateOf("Shop") }
    var coins by remember { mutableStateOf(0) }
    var characterName by remember { mutableStateOf("Musk") } // Nombre dinámico

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer) // Fondo azul clarito
            .padding(16.dp)
    ) {
        // Header con monedas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.chiguicoin_png),
                contentDescription = "Coins",
                modifier = Modifier.size(45.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = coins.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del personaje
        Text(
            text = characterName,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.secondary // Azul oscuro
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Personaje
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_shop_png),
                contentDescription = "Character",
                modifier = Modifier.size(160.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs Inventory / Shop
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { selectedTab = "Inventory" }
            ) {
                Text(
                    text = "Inventory",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.secondary, // Azul oscuro
                        fontWeight = if (selectedTab == "Inventory") FontWeight.Bold else FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (selectedTab == "Inventory") {
                    Box(
                        modifier = Modifier
                            .height(3.dp)
                            .fillMaxWidth()
                            .background(Color.Green)
                    )
                } else {
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { selectedTab = "Shop" }
            ) {
                Text(
                    text = "Shop",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 30.sp,
                        color = MaterialTheme.colorScheme.secondary, // Azul oscuro
                        fontWeight = if (selectedTab == "Shop") FontWeight.Bold else FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                if (selectedTab == "Shop") {
                    Box(
                        modifier = Modifier
                            .height(3.dp)
                            .fillMaxWidth()
                            .background(Color.Green)
                    )
                } else {
                    Spacer(modifier = Modifier.height(3.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido dinámico
        if (selectedTab == "Shop") {
            ShopSection(onBuy = { coins += it })
        } else {
            InventorySection()
        }
    }
}

@Composable
fun ShopSection(onBuy: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ShopItem("Sunglasses", 80, Icons.Default.Visibility, onBuy)
        ShopItem("Scarf", 120, Icons.Default.Style, onBuy)
    }
}

@Composable
fun ShopItem(name: String, price: Int, icon: androidx.compose.ui.graphics.vector.ImageVector, onBuy: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)) // Azul oscuro
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = name,
            tint = MaterialTheme.colorScheme.secondaryContainer, // Azul clarito
            modifier = Modifier.size(52.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.secondaryContainer, // Azul clarito
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$price", color = Color.Yellow, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.chiguicoin_png),
                contentDescription = "Coin icon",
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onBuy(price) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer) // Verde claro
        ) {
            Text("Buy", color = MaterialTheme.colorScheme.secondary, fontSize = 18.sp) // Azul oscuro
        }
    }
}

@Composable
fun InventorySection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        InventoryItem("T-shirt", Icons.Default.Checkroom)
        InventoryItem("Cap", Icons.Default.Face)
    }
}

@Composable
fun InventoryItem(name: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)) // Azul oscuro
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = name,
            tint = MaterialTheme.colorScheme.secondaryContainer, // Azul clarito
            modifier = Modifier.size(52.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.secondaryContainer, // Azul clarito
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer) // Verde claro
        ) {
            Text("Equip", color = MaterialTheme.colorScheme.secondary, fontSize = 18.sp) // Azul oscuro
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1F2B)
@Composable
fun StoreScreenPreview() {
    Muskly_TrainWithMeTheme {
        StoreScreen()
    }
}
