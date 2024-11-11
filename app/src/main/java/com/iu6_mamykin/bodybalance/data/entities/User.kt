package com.iu6_mamykin.bodybalance.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iu6_mamykin.bodybalance.data.Gender
import java.util.Date

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val birthDate: Date,
    val gender: Gender
)

