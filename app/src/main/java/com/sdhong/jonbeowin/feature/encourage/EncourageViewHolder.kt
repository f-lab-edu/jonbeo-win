package com.sdhong.jonbeowin.feature.encourage

import android.view.View
import com.sdhong.jonbeowin.base.BaseViewHolder
import com.sdhong.jonbeowin.databinding.ItemEncourageBinding
import com.sdhong.jonbeowin.feature.encourage.model.EncourageItem

class EncourageViewHolder(
    private val binding: ItemEncourageBinding
) : BaseViewHolder<EncourageItem>(binding.root) {

    override fun bind(item: EncourageItem) {
        binding.textViewEncourage.text = item.encourage.content

        binding.checkboxEncourage.also {
            it.visibility = if (item.isEditMode) View.VISIBLE else View.INVISIBLE
            it.isChecked = item.isChecked
        }

        binding.root.isClickable = item.isEditMode
        binding.checkboxEncourage.isClickable = item.isEditMode
    }
}