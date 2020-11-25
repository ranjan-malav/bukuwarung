package com.ranjan.malav.bukuwarung.api

import com.ranjan.malav.bukuwarung.data.User

class UserResponse {
    var page: Int = 0
    var per_page: Int = 0
    var total: Int = 0
    var total_pages: Int = 0
    lateinit var data: List<User>
}