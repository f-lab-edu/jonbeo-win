package com.sdhong.jonbeowin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sdhong.jonbeowin.base.BaseActivity
import com.sdhong.jonbeowin.databinding.ActivityAddAssetBinding

class AddAssetActivity : BaseActivity<ActivityAddAssetBinding>(
    bindingFactory = ActivityAddAssetBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AddAssetActivity::class.java)
        }
    }
}