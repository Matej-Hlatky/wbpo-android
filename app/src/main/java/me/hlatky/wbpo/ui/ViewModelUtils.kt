package me.hlatky.wbpo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.hlatky.wbpo.ui.UIState.Companion.asUIState

/**
 * First sets [UIState.Loading] and then [UIState.Success] or [UIState.Failure]
 * from [action] invocation.
 */
inline fun <T> ViewModel.invokeAction(
    target: MutableLiveData<UIState<T>>,
    crossinline action: suspend () -> T
): Unit = target.run {
    postValue(UIState.Loading)

    viewModelScope.launch {
        val result = runCatching { action() }
        val state = result.asUIState()

        postValue(state)
    }
}
