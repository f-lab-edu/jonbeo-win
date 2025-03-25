package com.sdhong.jonbeowin.feature.encourage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdhong.jonbeowin.databinding.ItemEncourageBinding
import com.sdhong.jonbeowin.feature.encourage.model.EncourageItem
import com.sdhong.jonbeowin.local.model.Encourage

class EncourageListAdapter(
    private val onEncourageItemClick: (encourage: Encourage) -> Unit
) : ListAdapter<EncourageItem, EncourageViewHolder>(
    object : ItemCallback<EncourageItem>() {
        override fun areItemsTheSame(oldItem: EncourageItem, newItem: EncourageItem): Boolean {
            return oldItem.encourage.id == newItem.encourage.id
        }

        override fun areContentsTheSame(oldItem: EncourageItem, newItem: EncourageItem): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncourageViewHolder {
        val binding = ItemEncourageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = EncourageViewHolder(binding)
        val onClick = { _: View ->
            val position = viewHolder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onEncourageItemClick(getItem(position).encourage)
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