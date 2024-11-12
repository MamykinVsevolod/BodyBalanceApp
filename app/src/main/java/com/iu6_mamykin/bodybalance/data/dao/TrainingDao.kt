package com.iu6_mamykin.bodybalance.data.dao

import androidx.room.*
import com.iu6_mamykin.bodybalance.data.entities.Training

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(training: Training): Long

    @Query("SELECT * FROM Trainings WHERE isCompleted = :isCompleted ORDER BY dateTime ASC")
    suspend fun getTrainingsByCompletionStatus(isCompleted: Boolean): List<Training>

    @Delete
    suspend fun delete(training: Training)

    @Query("SELECT * FROM Trainings WHERE trainingId = :trainingId")
    suspend fun getTrainingById(trainingId: Int): Training?

    @Query("UPDATE Trainings SET isCompleted = :isCompleted WHERE trainingId = :trainingId")
    suspend fun updateTrainingCompletionStatus(trainingId: Int, isCompleted: Boolean)

    @Query("DELETE FROM Trainings WHERE trainingId = :trainingId")
    suspend fun deleteTrainingById(trainingId: Int)

    @Query("SELECT * FROM Trainings ORDER BY trainingId DESC LIMIT 1")
    suspend fun getLatestTraining(): Training?
}