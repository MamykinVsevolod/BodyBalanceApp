package com.iu6_mamykin.bodybalance.data.dao

import androidx.room.*
import com.iu6_mamykin.bodybalance.data.entities.WorkoutName

@Dao
interface WorkoutNameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workoutName: WorkoutName): Long

    @Query("SELECT * FROM WorkoutNames")
    suspend fun getAllWorkoutNames(): List<WorkoutName>

    @Delete
    suspend fun delete(workoutName: WorkoutName)

    @Query("SELECT workoutNameId FROM WorkoutNames WHERE name = :name LIMIT 1")
    suspend fun getWorkoutNameIdByName(name: String): Int?
}