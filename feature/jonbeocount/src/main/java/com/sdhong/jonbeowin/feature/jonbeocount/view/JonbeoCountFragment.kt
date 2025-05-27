package com.sdhong.jonbeowin.feature.jonbeocount.view

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.sdhong.jonbeowin.core.common.base.BaseFragment
import com.sdhong.jonbeowin.core.common.extension.collectFlow
import com.sdhong.jonbeowin.core.common.navigation.MainNavigator
import com.sdhong.jonbeowin.feature.jonbeocount.component.JonbeoCountContent
import com.sdhong.jonbeowin.feature.jonbeocount.databinding.FragmentJonbeoCountBinding
import com.sdhong.jonbeowin.feature.jonbeocount.viewmodel.JonbeoCountViewModel
import com.sdhong.jonbeowin.feature.jonbeocount.viewmodel.JonbeoCountViewModel.JonbeoCountEvent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class JonbeoCountFragment : BaseFragment<FragmentJonbeoCountBinding>(
    bindingFactory = FragmentJonbeoCountBinding::inflate
) {

    @Inject
    lateinit var mainNavigator: MainNavigator

    private val viewModel: JonbeoCountViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setCollectors()
    }

    private fun setUpView() {
        binding.composeView.setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            JonbeoCountContent(
                uiState = uiState,
                onJonbeoCountItemClick = viewModel::onJonbeoCountItemClick,
                onAddAssetButtonClick = viewModel::eventStartAddAsset,
                toggleEditMode = viewModel::toggleEditMode
            )
        }
    }

    private fun setCollectors() {
        viewLifecycleOwner.collectFlow(viewModel.eventFlow) { event ->
            when (event) {
                is JonbeoCountEvent.StartAsset -> {
                    findNavController().navigate(
                        mainNavigator.getAssetDirections(event.assetId)
                    )
                }
            }
        }
    }
}