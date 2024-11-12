package com.iu6_mamykin.bodybalance.data.dao

import androidx.room.*
import com.iu6_mamykin.bodybalance.data.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)  // Метод перезаписывает старую запись

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): User?

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

    // Вставляем запись, удаляя старую, если существует
    @Transaction
    suspend fun insertSingleUser(user: User) {
        deleteAllUsers()
        insertUser(user)
    }

}