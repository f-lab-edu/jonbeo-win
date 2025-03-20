package com.sdhong.jonbeowin.view

import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseViewHolder
import com.sdhong.jonbeowin.databinding.ItemAssetBinding
import com.sdhong.jonbeowin.local.model.Asset

class JonbeoCountViewHolder(
    private val binding: ItemAssetBinding
) : BaseViewHolder<Asset>(binding.root) {

    override fun bind(item: Asset) {
        binding.textViewAssetName.text = item.name
        binding.textViewAssetDayCount.text =
            itemView.context.getString(R.string.asset_day_count, item.dayCount)
    }
}