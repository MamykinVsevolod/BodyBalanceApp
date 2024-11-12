package com.iu6_mamykin.bodybalance

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.iu6_mamykin.bodybalance.data.Gender
import com.iu6_mamykin.bodybalance.data.entities.User
import com.iu6_mamykin.bodybalance.navigation.AppNavHost
import com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen.createNotificationChannel
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestExactAlarmPermission()
        createNotificationChannel(this)
        // val notificationRoute = intent.getStringExtra("destination_route")
        // Получаем базу данных из MyApplication
        val database = (applicationContext as MyApplication).database

        setContent {
            BodyBalanceTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController, database = database)
            }
        }
    }
    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }
    }
}
