package com.example.muskly_trainwithme.ui.tips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muskly_trainwithme.R
import com.example.muskly_trainwithme.tipscreen.TipsViewModel
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme

@Composable
fun TipsScreen(viewModel: TipsViewModel = viewModel()) {

    val categories = viewModel.categoriesWithTips
    val expandedIndex = viewModel.expandedIndex

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.tips),
                    contentDescription = "Mascot",
                    modifier = Modifier
                        .size(110.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Tips and advices",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 18.dp)
                )
            }

            categories.forEach { (categoryName, tips) ->
                item {
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        fontSize = 28.sp,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
                }

                itemsIndexed(tips) { tipIndex, tip ->
                    val index = "$categoryName-$tipIndex"

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = tip.short,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.White
                                )
                                IconButton(
                                    onClick = { viewModel.toggleExpanded(index) },
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primaryContainer,
                                            shape = CircleShape
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Expand",
                                        // Cambiado a azul oscuro del rectángulo
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            if (expandedIndex == index) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = tip.details,
                                    fontSize = 14.sp,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1F2B)
@Composable
fun TipsScreenPreview() {
    Muskly_TrainWithMeTheme {
        TipsScreen()
    }
}
