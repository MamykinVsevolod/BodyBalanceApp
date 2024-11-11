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

        /*GlobalScope.launch {
            val calendar = Calendar.getInstance()
            calendar.set(2024, Calendar.APRIL, 30)  // Устанавливаем дату 30 апреля 2024
            val birthDate = calendar.time

            val newUser = User(
                name = "Дарья",
                email = "darya.thebest@gmail.com",
                birthDate = birthDate,
                gender = Gender.FEMALE
            )
            val userDao = database.userDao()
            userDao.insertUser(newUser)
        }*/

        setContent {
            BodyBalanceTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController, database = database)
            }
        }
    }
}
