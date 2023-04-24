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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.hlatky.wbpo.MainViewModel
import me.hlatky.wbpo.R
import me.hlatky.wbpo.Route
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.util.setupToolbar

/**
 * A fragment representing a list of [User].
 */
@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: UserListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = UserListRecyclerViewAdapter().also {
            it.changeUserIsFollowListener =
                UserListRecyclerViewAdapter.OnChangeUserIsFollowListener(
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
            list.adapter = adapter
        }

        if (viewModel.list.value.isEmpty()) {
            viewModel.loadMore()
        }

        // Sync ViewModel list with adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.list.collect(adapter::submitList)
        }
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
