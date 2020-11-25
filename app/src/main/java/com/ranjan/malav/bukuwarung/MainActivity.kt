package com.ranjan.malav.bukuwarung

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.ranjan.malav.bukuwarung.data.AppDatabase
import com.ranjan.malav.bukuwarung.data.DataRepository
import com.ranjan.malav.bukuwarung.data.User
import com.ranjan.malav.bukuwarung.data.UserDao
import com.ranjan.malav.bukuwarung.ui.home.HomeViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.getUsers().observe(this, Observer<List<User>> { users ->
            // Save data to room
            val db = AppDatabase.getDatabase(applicationContext)
            Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe { db.userDao().insertAll(users) }
        })
    }
}