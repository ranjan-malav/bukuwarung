package com.ranjan.malav.bukuwarung.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "first_name") val first_name: String,
    @ColumnInfo(name = "last_name") val last_name: String,
    @ColumnInfo(name = "avatar") val avatar: String
)