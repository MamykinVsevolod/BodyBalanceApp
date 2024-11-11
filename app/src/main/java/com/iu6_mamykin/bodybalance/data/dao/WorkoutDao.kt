package com.iu6_mamykin.bodybalance.data.dao

import androidx.room.*
import com.iu6_mamykin.bodybalance.data.entities.Workout

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout): Long

    @Query("SELECT * FROM Workout WHERE trainingId = :trainingId")
    suspend fun getWorkoutsByTrainingId(trainingId: Int): List<Workout>

    @Delete
    suspend fun delete(workout: Workout)
}