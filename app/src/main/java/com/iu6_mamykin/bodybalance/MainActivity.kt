package com.iu6_mamykin.bodybalance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.iu6_mamykin.bodybalance.data.Gender
import com.iu6_mamykin.bodybalance.data.entities.User
import com.iu6_mamykin.bodybalance.navigation.AppNavHost
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Получаем базу данных из MyApplication
        val database = (applicationContext as MyApplication).database

        setContent {
            BodyBalanceTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController, database = database)
            }
        }
    }
}
