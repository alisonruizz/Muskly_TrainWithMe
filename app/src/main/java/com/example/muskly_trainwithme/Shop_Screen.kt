package com.example.muskly_trainwithme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme

data class Item(
    val name: String,
    val price: Int,
    val icon: ImageVector,
    var isEquipped: Boolean = false
)

@Composable
fun ShopScreen() {
    var selectedTab by remember { mutableStateOf("Shop") }
    var coins by rememberSaveable { mutableIntStateOf(0) }
    var characterName by rememberSaveable { mutableStateOf("Musk") }

    var shopItems by rememberSaveable {
        mutableStateOf(
            listOf(
                Item("Sunglasses", 80, Icons.Default.Visibility),
                Item("Scarf", 120, Icons.Default.Style)
            )
        )
    }
    var inventoryItems by rememberSaveable {
        mutableStateOf(
            listOf(
                Item("T-shirt", 0, Icons.Default.Checkroom),
                Item("Cap", 0, Icons.Default.Face)
            )
        )
    }

    var itemToBuy by remember { mutableStateOf<Item?>(null) } // para la confirmación

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
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
                color = MaterialTheme.colorScheme.secondary
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
            TabItem(
                title = "Inventory",
                isSelected = selectedTab == "Inventory",
                onClick = { selectedTab = "Inventory" },
                modifier = Modifier.weight(1f)
            )
            TabItem(
                title = "Shop",
                isSelected = selectedTab == "Shop",
                onClick = { selectedTab = "Shop" },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido dinámico
        if (selectedTab == "Shop") {
            ShopSection(
                items = shopItems,
                onBuy = { item -> itemToBuy = item }
            )
        } else {
            InventorySection(
                items = inventoryItems,
                onToggleEquip = { item ->
                    inventoryItems = inventoryItems.map {
                        if (it.name == item.name) it.copy(isEquipped = !it.isEquipped) else it
                    }
                }
            )
        }
    }

    // Diálogo de confirmación de compra
    itemToBuy?.let { item ->
        AlertDialog(
            onDismissRequest = { itemToBuy = null },
            title = { Text("Confirm Purchase") },
            text = { Text("Are you sure you want to buy ${item.name}?") },
            confirmButton = {
                TextButton(onClick = {
                    shopItems = shopItems - item
                    inventoryItems = inventoryItems + item
                    itemToBuy = null
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToBuy = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isSelected) {
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

@Composable
fun ShopSection(items: List<Item>, onBuy: (Item) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            ShopItem(item = item, onBuy = { onBuy(item) })
        }
    }
}

@Composable
fun ShopItem(item: Item, onBuy: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            tint = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.size(52.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.name,
            color = MaterialTheme.colorScheme.secondaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${item.price}",
                color = Color.Yellow,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.chiguicoin_png),
                contentDescription = "Coin icon",
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onBuy() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text("Buy", color = MaterialTheme.colorScheme.secondary, fontSize = 18.sp)
        }
    }
}

@Composable
fun InventorySection(items: List<Item>, onToggleEquip: (Item) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            InventoryItem(item = item, onToggleEquip = { onToggleEquip(item) })
        }
    }
}

@Composable
fun InventoryItem(item: Item, onToggleEquip: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.name,
            tint = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.size(52.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.name,
            color = MaterialTheme.colorScheme.secondaryContainer,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onToggleEquip() },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (item.isEquipped) Color.Red else MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                if (item.isEquipped) "Quit" else "Equip",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1F2B)
@Composable
fun ShopScreenPreview() {
    Muskly_TrainWithMeTheme {
        ShopScreen()
    }
}
