package com.sdhong.jonbeowin.feature.jonbeocount.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdhong.jonbeowin.jonbeocount.databinding.ItemJonbeoCountBinding
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountModel

class JonbeoCountListAdapter(
    private val onJonbeoCountItemClick: (position: Int) -> Unit
) : ListAdapter<JonbeoCountModel, JonbeoCountViewHolder>(
    object : ItemCallback<JonbeoCountModel>() {
        override fun areItemsTheSame(oldItem: JonbeoCountModel, newItem: JonbeoCountModel): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: JonbeoCountModel, newItem: JonbeoCountModel): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JonbeoCountViewHolder {
        val binding = ItemJonbeoCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = JonbeoCountViewHolder(binding)
        val onClick = { _: View ->
            val position = viewHolder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onJonbeoCountItemClick(position)
            }
        }
        binding.root.setOnClickListener(onClick)
        binding.checkboxJonbeo.setOnClickListener(onClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: JonbeoCountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
