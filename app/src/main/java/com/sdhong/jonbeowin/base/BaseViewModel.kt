package com.sdhong.jonbeowin.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    final override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext

    protected fun <T> Flow<T>.stateIn(
        initialValue: T
    ): StateFlow<T> = stateIn(
        scope = this@BaseViewModel,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = initialValue
    )
}