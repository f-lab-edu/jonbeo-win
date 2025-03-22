package com.sdhong.jonbeowin

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sdhong.jonbeowin.base.BaseActivity
import com.sdhong.jonbeowin.databinding.ActivityMainBinding
import com.sdhong.jonbeowin.feature.encouragingword.EncouragingWordFragment
import com.sdhong.jonbeowin.feature.jonbeocount.JonbeoCountFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    bindingFactory = ActivityMainBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.jonbeoCount -> {
                    openFragment(JonbeoCountFragment())
                    true
                }

                R.id.encouragingWord -> {
                    openFragment(EncouragingWordFragment())
                    true
                }

                else -> false
            }
        }

        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.jonbeoCount
        }

        binding.bottomNav.setOnApplyWindowInsetsListener(null)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment)
            .commit()
    }
}