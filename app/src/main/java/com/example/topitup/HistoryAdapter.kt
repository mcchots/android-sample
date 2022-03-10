package com.example.topitup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topitup.database.history.History
import com.example.topitup.database.user.User
import com.example.topitup.databinding.HistoryItemBinding
import com.example.topitup.databinding.UsersItemBinding

class HistoryAdapter(
) : ListAdapter<History, HistoryAdapter.HistoryViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }
        }
    }

    class HistoryViewHolder(private var binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        //@SuppressLint("SimpleDateFormat")
        fun bind(history: History) {
            binding.tvTransactionUser.text = history.usedId.toString()
            binding.tvCardNumber.text = history.cardNumber
            binding.tvDate.text = history.date
            binding.tvTime.text = history.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.HistoryViewHolder {
        val viewHolder = HistoryAdapter.HistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        /*viewHolder.itemView.setOnClickListener {
            *//*val position = viewHolder.bindingAdapterPosition
            onItemClicked(getItem(position))*//*
        }*/
        return viewHolder
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}