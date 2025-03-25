package com.sdhong.jonbeowin.feature.jonbeocount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdhong.jonbeowin.databinding.ItemJonbeoCountBinding
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountItem
import com.sdhong.jonbeowin.local.model.Asset

class JonbeoCountListAdapter(
    private val onJonbeoCountItemClick: (asset: Asset) -> Unit
) : ListAdapter<JonbeoCountItem, JonbeoCountViewHolder>(
    object : ItemCallback<JonbeoCountItem>() {
        override fun areItemsTheSame(oldItem: JonbeoCountItem, newItem: JonbeoCountItem): Boolean =
            oldItem.asset.id == newItem.asset.id

        override fun areContentsTheSame(oldItem: JonbeoCountItem, newItem: JonbeoCountItem): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JonbeoCountViewHolder {
        val binding = ItemJonbeoCountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = JonbeoCountViewHolder(binding)
        val onClick = { _: View ->
            val position = viewHolder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onJonbeoCountItemClick(getItem(position).asset)
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
