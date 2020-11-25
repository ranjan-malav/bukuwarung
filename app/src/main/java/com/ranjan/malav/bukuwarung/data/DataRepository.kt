package com.ranjan.malav.bukuwarung.data

import androidx.annotation.WorkerThread

class DataRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    val allUsers: List<User> = userDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(users: List<User>) {
        userDao.insertAll(users)
    }
}