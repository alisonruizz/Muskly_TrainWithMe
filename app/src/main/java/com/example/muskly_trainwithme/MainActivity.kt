package com.example.muskly_trainwithme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.muskly_trainwithme.trainscreen.trainScreen
import com.example.muskly_trainwithme.ui.theme.Muskly_TrainWithMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Muskly_TrainWithMeTheme {
                //Scaffold { paddingValues ->
                  //  AppNavigation(modifier = androidx.compose.ui.Modifier.padding(paddingValues))
                //}
                trainScreen()
            }
        }
    }
}
