package com.ranjan.malav.bukuwarung.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.ranjan.malav.bukuwarung.R

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val firstName = root.findViewById<TextInputLayout>(R.id.first_name)
        val lastName = root.findViewById<TextInputLayout>(R.id.last_name)
        val email = root.findViewById<TextInputLayout>(R.id.email)
        val saveButton = root.findViewById<FloatingActionButton>(R.id.profile_save)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref?.edit()

        sharedPref?.also {
            firstName.editText?.setText(it.getString("user_first_name", ""))
            lastName.editText?.setText(it.getString("user_last_name", ""))
            email.editText?.setText(it.getString("user_email", ""))
        }

        saveButton.setOnClickListener {
            sharedPrefEditor?.also {
                it.putString("user_first_name", firstName.editText?.text.toString())
                it.putString("user_last_name", lastName.editText?.text.toString())
                it.putString("user_email", email.editText?.text.toString())
            }
            sharedPrefEditor?.apply()
        }

        return root
    }
}