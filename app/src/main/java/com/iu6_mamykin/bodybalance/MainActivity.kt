package com.iu6_mamykin.bodybalance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.iu6_mamykin.bodybalance.navigation.AppNavHost
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BodyBalanceTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
