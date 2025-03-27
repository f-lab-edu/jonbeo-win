package com.sdhong.jonbeowin.feature.jonbeocount

import android.view.View
import com.sdhong.jonbeowin.R
import com.sdhong.jonbeowin.base.BaseViewHolder
import com.sdhong.jonbeowin.databinding.ItemJonbeoCountBinding
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountItem

class JonbeoCountViewHolder(
    private val binding: ItemJonbeoCountBinding
) : BaseViewHolder<JonbeoCountItem>(binding.root) {

    override fun bind(item: JonbeoCountItem) {
        binding.textViewAssetName.text = item.asset.name
        binding.textViewAssetDayCount.text =
            itemView.context.getString(R.string.jonbeo_day_count, item.asset.dayCount)

        binding.imageViewChevron.visibility = if (item.isEditMode) View.GONE else View.VISIBLE
        binding.checkboxJonbeo.also {
            it.visibility = if (item.isEditMode) View.VISIBLE else View.GONE
            it.isChecked = item.isChecked
        }
    }
}