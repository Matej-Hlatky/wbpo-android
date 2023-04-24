package me.hlatky.wbpo.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.hlatky.wbpo.R
import me.hlatky.wbpo.model.User

/**
 * A fragment representing a list of [User].
 */
@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModels()
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

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }
}
