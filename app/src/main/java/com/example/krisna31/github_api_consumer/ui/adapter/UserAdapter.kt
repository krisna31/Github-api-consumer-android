package com.example.krisna31.github_api_consumer.ui.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.krisna31.github_api_consumer.data.response.SearchUserItem
import com.example.krisna31.github_api_consumer.databinding.ItemUserBinding
import com.example.krisna31.github_api_consumer.ui.detail_user.DetailUserActivity

class UserAdapter : ListAdapter<SearchUserItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: SearchUserItem) {
            Glide.with(itemView.context)
                .load(userItem.avatarUrl)
                .into(binding.civUser)
            binding.tvUsername.text = userItem.login
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, userItem.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchUserItem>() {
            override fun areItemsTheSame(
                oldItem: SearchUserItem,
                newItem: SearchUserItem
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: SearchUserItem,
                newItem: SearchUserItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}