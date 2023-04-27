package me.hlatky.wbpo.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.hlatky.wbpo.MainViewModel
import me.hlatky.wbpo.R
import me.hlatky.wbpo.Route
import me.hlatky.wbpo.databinding.FragmentUserListBinding
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.ui.GridSpacingItemDecoration
import me.hlatky.wbpo.ui.RecyclerViewScrollButtonHelper
import me.hlatky.wbpo.util.setupToolbar

/**
 * A fragment representing a list of [User].
 */
@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UserListAdapter
    private lateinit var errorHandler: UserListLoadErrorHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = UserListAdapter().also {
            it.changeUserIsFollowListener = UserListAdapter.OnChangeUserIsFollowListener(
                viewModel::updateUserFollowing
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        // Note, ViewBinding impl. would be enough
    ): View = FragmentUserListBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup toolbar
        (requireActivity() as AppCompatActivity?)?.setupToolbar(view.findViewById(R.id.toolbar))

        // Setup menu
        requireActivity().addMenuProvider(
            UserListMenuProvider(::requestLogout),
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        errorHandler = UserListLoadErrorHandler(view, adapter, viewLifecycleOwner)

        // Setup list
        binding.list.also {
            val spacingPx = resources.getDimensionPixelSize(R.dimen.user_grid_spacing)

            it.addItemDecoration(GridSpacingItemDecoration(spacingPx))
            it.adapter = adapter

            RecyclerViewScrollButtonHelper(recyclerView = it, target = binding.button)
            // TODO Try withLoadStateHeaderAndFooter for footer and header
        }

        viewLifecycleOwner.lifecycleScope.launch {
            // Sync ViewModel list with adapter
            viewModel.list.collect(adapter::submitData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        errorHandler.destroy()
    }

    private fun requestLogout() {
        viewModel.clearUserSession()
        activityViewModel.selectRoute(Route.USER_LOGIN)
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }
}
