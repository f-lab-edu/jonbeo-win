package com.sdhong.jonbeowin.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import com.sdhong.jonbeowin.databinding.ItemAssetBinding
import com.sdhong.jonbeowin.local.model.Asset

class JonbeoCountListAdapter : ListAdapter<Asset, JonbeoCountViewHolder>(
    object : ItemCallback<Asset>() {
        override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean =
            oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JonbeoCountViewHolder {
        return JonbeoCountViewHolder(
            ItemAssetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JonbeoCountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
