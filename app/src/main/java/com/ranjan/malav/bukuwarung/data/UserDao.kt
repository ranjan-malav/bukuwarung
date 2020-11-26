package com.ranjan.malav.bukuwarung.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM user WHERE id IS (:userId)")
    fun loadAllByIds(userId: Int): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)
}