package com.ranjan.malav.bukuwarung.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.gson.reflect.TypeToken
import com.ranjan.malav.bukuwarung.api.GsonRequest
import com.ranjan.malav.bukuwarung.api.UserResponse
import com.ranjan.malav.bukuwarung.data.User


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
        val queue = Volley.newRequestQueue(getApplication())
        val url = "https://reqres.in/api/users"

        val myReq: GsonRequest<UserResponse> = GsonRequest(
            url,
            UserResponse::class.java,
            null,
            Response.Listener { response ->
                users.value = response.data
            },
            Response.ErrorListener { error ->
                Log.d(
                    "HomeViewModel",
                    "loadUsers: error - " + error.message
                )
            }
        )

        queue.add(myReq)
    }

}