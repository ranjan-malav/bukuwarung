package com.ranjan.malav.bukuwarung.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ranjan.malav.bukuwarung.R
import com.ranjan.malav.bukuwarung.data.User
import com.squareup.picasso.Picasso

class UserAdapter(private val users: List<User>, private val onClick: (User) -> Unit) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val email: TextView
        val avatar: ImageView

        init {
            name = view.findViewById(R.id.user_full_name)
            email = view.findViewById(R.id.user_email)
            avatar = view.findViewById(R.id.user_avatar)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_row_user, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val fullName = users[position].first_name + " " + users[position].last_name
        viewHolder.name.text = fullName
        viewHolder.email.text = users[position].email
        Picasso.get().load(users[position].avatar).into(viewHolder.avatar)

        viewHolder.itemView.setOnClickListener {
            onClick(users[position])
        }
    }

    override fun getItemCount() = users.size

}
