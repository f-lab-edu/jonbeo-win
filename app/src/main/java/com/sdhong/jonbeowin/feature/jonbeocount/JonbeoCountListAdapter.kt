package com.sdhong.jonbeowin.feature.jonbeocount

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sdhong.jonbeowin.databinding.ItemAssetBinding
import com.sdhong.jonbeowin.local.model.Asset
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.AssetUiState

class JonbeoCountListAdapter(
    private val onAssetItemClick: (asset: Asset) -> Unit
) : ListAdapter<AssetUiState, JonbeoCountViewHolder>(
    object : ItemCallback<AssetUiState>() {
        override fun areItemsTheSame(oldItem: AssetUiState, newItem: AssetUiState): Boolean =
            oldItem.asset.id == newItem.asset.id

        override fun areContentsTheSame(oldItem: AssetUiState, newItem: AssetUiState): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JonbeoCountViewHolder {
        val binding = ItemAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = JonbeoCountViewHolder(binding)
        val onClick = { _: View ->
            val position = viewHolder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onAssetItemClick(getItem(position).asset)
            }
        }
        binding.root.setOnClickListener(onClick)
        binding.checkboxEdit.setOnClickListener(onClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: JonbeoCountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
