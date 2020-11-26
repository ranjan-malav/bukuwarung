package com.ranjan.malav.bukuwarung.ui.user_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.textfield.TextInputLayout
import com.ranjan.malav.bukuwarung.R
import com.squareup.picasso.Picasso

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val USER_FIRST_NAME = "user_first_name"
        const val USER_LAST_NAME = "user_last_name"
        const val USER_EMAIL = "user_email"
        const val USER_AVATAR = "user_avatar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        val firstName = findViewById<TextInputLayout>(R.id.first_name)
        firstName.isEnabled = false
        val lastName = findViewById<TextInputLayout>(R.id.last_name)
        lastName.isEnabled = false
        val email = findViewById<TextInputLayout>(R.id.email)
        email.isEnabled = false
        val avatar = findViewById<ImageView>(R.id.profile_image)
        val fab = findViewById<ImageView>(R.id.profile_save)
        fab.visibility = View.GONE

        if (intent != null) {
            firstName.editText?.setText(intent.getStringExtra(USER_FIRST_NAME))
            lastName.editText?.setText(intent.getStringExtra(USER_LAST_NAME))
            email.editText?.setText(intent.getStringExtra(USER_EMAIL))
            Picasso.get().load(intent.getStringExtra(USER_AVATAR)).into(avatar)
        }
    }
}