package com.iu6_mamykin.bodybalance.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "Trainings",
    foreignKeys = [
        ForeignKey(
            entity = TrainingName::class,
            parentColumns = ["trainingNameId"],
            childColumns = ["trainingNameId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Training(
    @PrimaryKey(autoGenerate = true) val trainingId: Int = 0,
    val trainingNameId: Int, // FK to TrainingNames
    val dateTime: Date,             // Хранит дату и время тренировки
    val reminderDateTime: Date? = null,  // Хранит дату и время напоминания, если есть
    val isCompleted: Boolean = false
)
