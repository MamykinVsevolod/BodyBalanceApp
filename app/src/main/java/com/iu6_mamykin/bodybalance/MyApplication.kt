package com.iu6_mamykin.bodybalance

import android.app.Application
import androidx.room.Room
import com.iu6_mamykin.bodybalance.data.AppDatabase

class MyApplication : Application() {
    // Единственный экземпляр базы данных, доступный по всему приложению
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        // Инициализация базы данных
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database" // название базы данных
        ).build()
    }
}