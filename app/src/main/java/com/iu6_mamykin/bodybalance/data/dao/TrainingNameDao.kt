package com.iu6_mamykin.bodybalance.data.dao

import androidx.room.*
import com.iu6_mamykin.bodybalance.data.entities.TrainingName

@Dao
interface TrainingNameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trainingName: TrainingName): Long

    @Query("SELECT * FROM TrainingNames")
    suspend fun getAllTrainingNames(): List<TrainingName>

    @Delete
    suspend fun delete(trainingName: TrainingName)

    @Query("SELECT name FROM TrainingNames WHERE trainingNameId = :trainingNameId LIMIT 1")
    suspend fun getTrainingNameById(trainingNameId: Int): String?

    @Query("SELECT trainingNameId FROM TrainingNames WHERE name = :name LIMIT 1")
    suspend fun getTrainingNameIdByName(name: String): Int?

}