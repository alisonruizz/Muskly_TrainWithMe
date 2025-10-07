package com.example.muskly_trainwithme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme

class TrainStartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Muskly_TrainWithMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    TrainStartScreen()
                }
            }
        }
    }
}

@Composable
fun TrainStartScreen() {
    var xpProgress by remember { mutableStateOf(0.6f) } // progreso
    var currentXP by remember { mutableStateOf(370) }   // XP actual
    var maxXP by remember { mutableStateOf(500) }       // XP total para subir de nivel

    val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val progressColor = MaterialTheme.colorScheme.primaryContainer
    val barBackgroundColor = MaterialTheme.colorScheme.onSecondaryContainer

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        //Sección XP
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Musk XP",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                //Barra de XP
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(25.dp)
                        .clip(RoundedCornerShape(50))
                        .background(barBackgroundColor)
                ) {
                    // Parte verde que es el progreso
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(xpProgress)
                            .fillMaxHeight()
                            .background(progressColor)
                    )

                    // Texto dentro de la barra verde que indica el XP actual
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                    ) {
                        val offset = (xpProgress * 280).dp - 35.dp
                        Text(
                            text = "$currentXP",
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = offset.coerceIn(0.dp, 260.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // XP total
                Text(
                    text = "/ $maxXP XP",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Burbuja de diálogo y mascota
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy((-10).dp)
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface, shape = trainingSpeechBubbleShape())
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "I can't wait to start",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.capybara_train_png),
                contentDescription = "Mascota Musk",
                modifier = Modifier.size(160.dp),
                contentScale = ContentScale.Fit
            )
        }

        //Botón Start train
        Button(
            onClick = {
                if (currentXP < maxXP) {
                    currentXP += 10
                    xpProgress = currentXP.toFloat() / maxXP.toFloat()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = progressColor),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(55.dp)
        ) {
            Text(
                text = "Start train",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

fun trainingSpeechBubbleShape(): GenericShape {
    return GenericShape { size, _ ->
        val cornerRadius = 35f
        val pointerSize = 25f
        val pointerX = size.width / 2f

        moveTo(cornerRadius, 0f)
        lineTo(size.width - cornerRadius, 0f)
        quadraticBezierTo(size.width, 0f, size.width, cornerRadius)
        lineTo(size.width, size.height - cornerRadius)
        quadraticBezierTo(size.width, size.height, size.width - cornerRadius, size.height)
        lineTo(pointerX + pointerSize, size.height)
        lineTo(pointerX, size.height + pointerSize)
        lineTo(pointerX - pointerSize, size.height)
        lineTo(cornerRadius, size.height)
        quadraticBezierTo(0f, size.height, 0f, size.height - cornerRadius)
        lineTo(0f, cornerRadius)
        quadraticBezierTo(0f, 0f, cornerRadius, 0f)
        close()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrainStartScreenPreview() {
    Muskly_TrainWithMeTheme {
        TrainStartScreen()
    }
}
