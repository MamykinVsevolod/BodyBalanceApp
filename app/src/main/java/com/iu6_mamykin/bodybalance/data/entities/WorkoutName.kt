package com.iu6_mamykin.bodybalance.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WorkoutNames")
data class WorkoutName(
    @PrimaryKey(autoGenerate = true) val workoutNameId: Int = 0,
    val name: String
)
