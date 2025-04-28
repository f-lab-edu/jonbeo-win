package com.sdhong.jonbeowin.feature.encourage.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdhong.jonbeowin.feature.encourage.databinding.ItemEncourageBinding
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel

class EncourageListAdapter(
    private val onEncourageItemClick: (position: Int) -> Unit
) : ListAdapter<EncourageModel, EncourageViewHolder>(
    object : ItemCallback<EncourageModel>() {
        override fun areItemsTheSame(oldItem: EncourageModel, newItem: EncourageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EncourageModel, newItem: EncourageModel): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncourageViewHolder {
        val binding = ItemEncourageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = EncourageViewHolder(binding)
        val onClick = { _: View ->
            val position = viewHolder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onEncourageItemClick(position)
            }
        }
        binding.root.setOnClickListener(onClick)
        binding.checkboxEncourage.setOnClickListener(onClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: EncourageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}