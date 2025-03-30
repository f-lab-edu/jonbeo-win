package com.sdhong.jonbeowin.jonbeocount.view

import android.view.View
import com.sdhong.jonbeowin.common.base.BaseViewHolder
import com.sdhong.jonbeowin.jonbeocount.R
import com.sdhong.jonbeowin.jonbeocount.databinding.ItemJonbeoCountBinding
import com.sdhong.jonbeowin.jonbeocount.model.JonbeoCountModel

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