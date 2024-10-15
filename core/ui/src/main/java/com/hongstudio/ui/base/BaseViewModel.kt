package com.hongstudio.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    final override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext

    private val _isLoading = MutableStateFlow(false)

    @OptIn(FlowPreview::class)
    val isLoading: Flow<Boolean> = _isLoading.debounce(700L)

    fun launchWithLoading(block: suspend CoroutineScope.() -> Unit) {
        launch {
            _isLoading.value = true
            block()
            _isLoading.value = false
        }
    }
}
