package com.ranjan.malav.bukuwarung.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ranjan.malav.bukuwarung.R
import com.ranjan.malav.bukuwarung.data.AppDatabase
import com.ranjan.malav.bukuwarung.data.User
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.rv_users)

        homeViewModel.getUsers().observe(viewLifecycleOwner, Observer<List<User>> { users ->
            recyclerView.adapter = UserAdapter(users) { user -> adapterOnClick(user) }
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        })

        return root
    }

    private fun adapterOnClick(user: User) {
        Log.d("HomeFrag", "selected user: ${user.email}")
    }
}