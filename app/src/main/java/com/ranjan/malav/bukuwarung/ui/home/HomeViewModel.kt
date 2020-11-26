package com.ranjan.malav.bukuwarung.ui.home

import android.app.Application
import android.util.Log
import android.widget.Toast
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
import com.ranjan.malav.bukuwarung.data.AppDatabase
import com.ranjan.malav.bukuwarung.data.User
import com.ranjan.malav.bukuwarung.data.UserDao
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    fun getUsers(): LiveData<List<User>> {
        val db = AppDatabase.getDatabase(getApplication())
        return db.userDao().getAll()
    }

    fun loadUsers() {
        // Do an asynchronous operation to fetch users.
        val queue = Volley.newRequestQueue(getApplication())
        val url = "https://reqres.in/api/users"

        val myReq: GsonRequest<UserResponse> = GsonRequest(
            url,
            UserResponse::class.java,
            null,
            Response.Listener { response ->
                // Save data to room db
                val db = AppDatabase.getDatabase(getApplication())
                Observable.just(db)
                    .subscribeOn(Schedulers.io())
                    .subscribe { db.userDao().insertAll(response.data) }
            },
            Response.ErrorListener { error ->
                Toast.makeText(getApplication(), error.localizedMessage, Toast.LENGTH_SHORT).show()
                Log.d(
                    "HomeViewModel",
                    "loadUsers: error - " + error.message
                )
            }
        )

        queue.add(myReq)
    }

}