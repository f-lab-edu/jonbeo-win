package com.sdhong.jonbeowin.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdhong.jonbeowin.databinding.ItemAssetBinding
import com.sdhong.jonbeowin.local.model.Asset

class JonbeoCountListAdapter(
    private val onAssetItemClick: (asset: Asset) -> Unit
) : ListAdapter<Asset, JonbeoCountViewHolder>(
    object : ItemCallback<Asset>() {
        override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JonbeoCountViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = JonbeoCountViewHolder(binding)
        binding.root.setOnClickListener {
            val position = viewHolder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onAssetItemClick(getItem(position))
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: JonbeoCountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
