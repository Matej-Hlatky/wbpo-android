package me.hlatky.wbpo.ui.user.list

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import me.hlatky.wbpo.R
import me.hlatky.wbpo.util.NetworkStatusTracker
import me.hlatky.wbpo.util.getLocalizedUserFacingMessage
import me.hlatky.wbpo.util.provideService

/** Handlers the [adapter] load error in [UserListFragment]. */
class UserListLoadErrorHandler(
    private val root: View,
    private val adapter: PagingDataAdapter<*, *>,
    lifecycleOwner: LifecycleOwner,
) {
    private val resources = root.context.resources
    private val networkStatusTracker = NetworkStatusTracker(root.context.provideService())
    private var loadErrorSnackbar: Snackbar? = null

    init {
        adapter.addLoadStateListener(::onLoadStateChanged)

        lifecycleOwner.also {
            it.lifecycleScope.launch {
                it.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Observe Network status
                    networkStatusTracker.observe().collect(::onNetworkStatusChanged)
                }
            }
        }
    }

    fun destroy() {
        adapter.removeLoadStateListener(::onLoadStateChanged)
    }

    /** Called when [adapter] load state changed. */
    private fun onLoadStateChanged(state: CombinedLoadStates) {
        val firstError = (state.append as? LoadState.Error) ?: (state.prepend as? LoadState.Error)

        if (firstError != null) {
            onLoadError(firstError.error)
        } else {
            loadErrorSnackbar = null
        }
    }

    /** Called when [onLoadStateChanged] yields [error]. */
    private fun onLoadError(error: Throwable) {
        val errorText = error.getLocalizedUserFacingMessage(resources)

        // Using Snackbar intentionally for different UX than AlertDialog
        Snackbar
            .make(root, errorText, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.dialog_retry) {
                adapter.retry()
            }.also {
                loadErrorSnackbar = it
            }
            .show()
    }

    /** Called when [networkStatusTracker] [status] changed. */
    private fun onNetworkStatusChanged(status: NetworkStatusTracker.Status) {
        // Auto retry when have Internet again
        if (status == NetworkStatusTracker.Status.Available) {
            if (loadErrorSnackbar != null) {
                loadErrorSnackbar?.dismiss()
                loadErrorSnackbar = null
                adapter.retry()
            }
        }
    }
}
