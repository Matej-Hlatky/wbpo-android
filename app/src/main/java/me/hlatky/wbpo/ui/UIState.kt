package me.hlatky.wbpo.ui

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()

    data class Success<T>(val value: T) : UIState<T>()

    data class Failure<T>(val error: Throwable) : UIState<T>()

    inline fun fold(
        onLoadingStart: () -> Unit,
        onLoadingEnd: () -> Unit,
        onSuccess: (value: T) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ): Unit = when (this) {
        is Loading -> onLoadingStart()
        is Success -> {
            onLoadingEnd()
            onSuccess(value)
        }

        is Failure -> {
            onLoadingEnd()
            onFailure(error)
        }
    }

    companion object {
        fun <T> Result<T>.asUIState() = fold(
            onSuccess = ::Success,
            onFailure = ::Failure
        )
    }
}
