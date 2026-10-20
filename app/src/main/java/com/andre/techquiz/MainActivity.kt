package com.andre.techquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.andre.techquiz.navigation.AppNavigation
import com.andre.techquiz.presentation.theme.TechquizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechquizTheme {
                AppNavigation()
            }
        }
    }
}
