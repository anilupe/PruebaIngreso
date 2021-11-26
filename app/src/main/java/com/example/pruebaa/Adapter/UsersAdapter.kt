package com.example.pruebaa.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebaa.Model.User
import com.example.pruebaa.R
import com.example.pruebaa.ui.Post.Activity_post
import android.content.Intent

class UsersAdapter(private var users: MutableList<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(
            view

        )
    }

    override fun getItemCount(): Int {
        return users.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users.get(position)
        holder.firstName.text = user.name
        holder.phone.text = user.phone
        holder.mail.text = user.email
        val activity = holder.itemView.context as Activity


        holder.morePublishe.setOnClickListener {
            val intent = Intent(activity, Activity_post::class.java)
            intent.putExtra("idUser", user.id)
            intent.putExtra("nameUser", user.name)
            intent.putExtra("phoneUser", user.phone)
            intent.putExtra("emailUser", user.email)

            activity.startActivity(intent)
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val firstName: TextView = itemView.findViewById(R.id.tvName)
        val phone: TextView = itemView.findViewById(R.id.tvPhone)
        val mail: TextView = itemView.findViewById(R.id.tvMail)
        val morePublishe: TextView = itemView.findViewById(R.id.tvMoreP)
    }
}

