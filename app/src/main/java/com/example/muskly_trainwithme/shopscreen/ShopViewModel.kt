package com.example.muskly_trainwithme.shopscreen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.ui.graphics.vector.ImageVector

data class Item(
    val name: String,
    val price: Int,
    val icon: ImageVector,
    var isEquipped: Boolean = false
)

class ShopViewModel : ViewModel() {

    // --- Estado principal ---
    var coins by mutableStateOf(500)
        private set

    var characterName by mutableStateOf("Musk")
        private set

    var selectedTab by mutableStateOf("Shop")
        private set

    var shopItems by mutableStateOf(
        listOf(
            Item("Sunglasses", 80, Icons.Default.Visibility),
            Item("Scarf", 120, Icons.Default.Style)
        )
    )
        private set

    var inventoryItems by mutableStateOf(
        listOf(
            Item("T-shirt", 0, Icons.Default.Checkroom),
            Item("Cap", 0, Icons.Default.Face)
        )
    )
        private set

    var itemToBuy by mutableStateOf<Item?>(null)
        private set


    //Funciones de negocio

    fun selectTab(tab: String) {
        selectedTab = tab
    }

    fun showBuyDialog(item: Item) {
        itemToBuy = item
    }

    fun dismissDialog() {
        itemToBuy = null
    }

    fun confirmPurchase(item: Item) {
        if (coins >= item.price) {
            coins -= item.price
            shopItems = shopItems - item
            inventoryItems = inventoryItems + item
        }
        itemToBuy = null
    }

    fun toggleEquip(item: Item) {
        inventoryItems = inventoryItems.map {
            if (it.name == item.name) it.copy(isEquipped = !it.isEquipped) else it
        }
    }

    fun addCoins(amount: Int) {
        coins += amount
    }


}