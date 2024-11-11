package com.iu6_mamykin.bodybalance.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iu6_mamykin.bodybalance.data.dao.*
import com.iu6_mamykin.bodybalance.data.entities.*
@Database(
    entities = [TrainingName::class, WorkoutName::class, Training::class, Workout::class, User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trainingNameDao(): TrainingNameDao
    abstract fun workoutNameDao(): WorkoutNameDao
    abstract fun trainingDao(): TrainingDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun userDao(): UserDao
}