package com.sdhong.jonbeowin.feature.jonbeocount.view

import android.view.View
import com.sdhong.jonbeowin.core.common.base.BaseViewHolder
import com.sdhong.jonbeowin.feature.jonbeocount.R
import com.sdhong.jonbeowin.feature.jonbeocount.databinding.ItemJonbeoCountBinding
import com.sdhong.jonbeowin.feature.jonbeocount.model.JonbeoCountModel

class JonbeoCountViewHolder(
    private val binding: ItemJonbeoCountBinding
) : BaseViewHolder<JonbeoCountModel>(binding.root) {

    override fun bind(item: JonbeoCountModel) {
        binding.textViewAssetName.text = item.name
        binding.textViewAssetDayCount.text =
            itemView.context.getString(R.string.jonbeo_day_count, item.dayCount)

        binding.imageViewChevron.visibility = if (item.isEditMode) View.GONE else View.VISIBLE
        binding.checkboxJonbeo.also {
            it.visibility = if (item.isEditMode) View.VISIBLE else View.GONE
            it.isChecked = item.isChecked
        }
    }
}