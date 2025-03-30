package com.sdhong.jonbeowin.feature.encourage.view

import android.view.View
import com.sdhong.jonbeowin.feature.encourage.BaseViewHolder
import com.sdhong.jonbeowin.feature.encourage.databinding.ItemEncourageBinding
import com.sdhong.jonbeowin.feature.encourage.model.EncourageModel

class EncourageViewHolder(
    private val binding: ItemEncourageBinding
) : BaseViewHolder<EncourageModel>(binding.root) {

    override fun bind(item: EncourageModel) {
        binding.textViewEncourage.text = item.content

        binding.checkboxEncourage.also {
            it.visibility = if (item.isEditMode) View.VISIBLE else View.INVISIBLE
            it.isChecked = item.isChecked
        }

        binding.root.isClickable = item.isEditMode
        binding.checkboxEncourage.isClickable = item.isEditMode
    }
}