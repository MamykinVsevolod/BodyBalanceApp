package com.iu6_mamykin.bodybalance.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrainingNames")
data class TrainingName(
    @PrimaryKey(autoGenerate = true) val trainingNameId: Int = 0,
    val name: String
)
