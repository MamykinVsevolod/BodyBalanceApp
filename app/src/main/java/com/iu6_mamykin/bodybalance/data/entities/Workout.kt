package com.iu6_mamykin.bodybalance.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Workout",
    foreignKeys = [
        ForeignKey(
            entity = Training::class,
            parentColumns = ["trainingId"],
            childColumns = ["trainingId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutName::class,
            parentColumns = ["workoutNameId"],
            childColumns = ["workoutNameId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Workout(
    @PrimaryKey(autoGenerate = true) val workoutId: Int = 0,
    val trainingId: Int, // FK to Trainings
    val workoutNameId: Int, // FK to WorkoutNames
    val note: String? = null,
    val isCompleted: Boolean = false
)
