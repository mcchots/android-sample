package com.example.topitup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topitup.database.user.User
import com.example.topitup.databinding.TopUsersItemBinding


class UserAdapter(private val onItemClicked: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    class UserViewHolder(private var binding: TopUsersItemBinding): RecyclerView.ViewHolder(binding.root) {
        //@SuppressLint("SimpleDateFormat")
        fun bind(user: User) {
            binding.nameTextView.text = user.name
            binding.cardTextView.text = user.cardsScanned.toString()
            binding.pointsTextView.text = user.points.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val viewHolder = UserViewHolder(
            TopUsersItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
