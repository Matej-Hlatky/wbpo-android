package me.hlatky.wbpo.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.hlatky.wbpo.MainViewModel
import me.hlatky.wbpo.R
import me.hlatky.wbpo.Route
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.util.getLocalizedUserFacingMessage
import me.hlatky.wbpo.util.setupToolbar

/**
 * A fragment representing a list of [User].
 */
@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = UserListAdapter().also {
            it.changeUserIsFollowListener =
                UserListAdapter.OnChangeUserIsFollowListener(
                    viewModel::updateUserFollowing
                )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_user_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup toolbar
        (requireActivity() as AppCompatActivity?)?.setupToolbar(view.findViewById(R.id.toolbar))

        // Setup menu
        requireActivity().addMenuProvider(
            FragmentMenuProvider(), viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        // Setup list
        view.findViewById<RecyclerView>(R.id.list).also { list ->
            val columns = resources.getInteger(R.integer.user_grid_columns)

            list.layoutManager = GridLayoutManager(requireContext(), columns)
            list.adapter = adapter.also {
                it.addLoadStateListener { states ->
                    val firstError =
                        (states.append as? LoadState.Error) ?: (states.prepend as? LoadState.Error)

                    if (firstError != null) {
                        onLoadError(firstError.error)
                    }
                }
                // TODO Try withLoadStateHeaderAndFooter for footer and header
            }
        }

        // Sync ViewModel list with adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.list.collect(adapter::submitData)
        }
    }

    private fun onLoadError(error: Throwable) {
        val errorText = error.getLocalizedUserFacingMessage(resources)

        // Using Snackbar intentionally for different UX than AlertDialog
        Snackbar
            .make(requireView(), errorText, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.dialog_retry) {
                adapter.retry()
            }
            .show()
    }

    private fun requestLogout() {
        viewModel.clearUserSession()
        activityViewModel.selectRoute(Route.USER_LOGIN)
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }

    private inner class FragmentMenuProvider : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.fragment_user_list, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
            R.id.action_logout -> {
                requestLogout(); true
            }

            else -> false
        }
    }
}
