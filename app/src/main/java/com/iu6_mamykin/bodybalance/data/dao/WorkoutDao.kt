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

    @Query("UPDATE Workout SET isCompleted = :isCompleted WHERE workoutId = :workoutId")
    suspend fun updateWorkoutCompletion(workoutId: Int, isCompleted: Boolean)

    // Обновленный запрос для поиска по workoutNameId и note
    @Query("SELECT * FROM Workout WHERE workoutNameId = :workoutNameId AND note = :note")
    suspend fun getWorkoutsByNameAndNote(workoutNameId: Int, note: String): List<Workout>
}