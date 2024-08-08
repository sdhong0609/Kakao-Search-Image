package com.hongstudio.kakaosearchimage.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel: ViewModel(), CoroutineScope {

    final override val coroutineContext: CoroutineContext = viewModelScope.coroutineContext
}
