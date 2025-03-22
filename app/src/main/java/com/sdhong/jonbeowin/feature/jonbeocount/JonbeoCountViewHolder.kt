package com.sdhong.jonbeowin.feature.jonbeocount

import android.view.View
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseViewHolder
import com.sdhong.jonbeowin.databinding.ItemAssetBinding
import com.sdhong.jonbeowin.feature.jonbeocount.uistate.AssetUiState

class JonbeoCountViewHolder(
    private val binding: ItemAssetBinding
) : BaseViewHolder<AssetUiState>(binding.root) {

    override fun bind(item: AssetUiState) {
        binding.textViewAssetName.text = item.asset.name
        binding.textViewAssetDayCount.text =
            itemView.context.getString(R.string.asset_day_count, item.asset.dayCount)

        binding.imageViewChevron.visibility = if (item.isEditMode) View.GONE else View.VISIBLE
        binding.checkboxEdit.also {
            it.visibility = if (item.isEditMode) View.VISIBLE else View.GONE
            it.isChecked = item.isChecked
        }
    }
}