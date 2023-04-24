package me.hlatky.wbpo.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import me.hlatky.wbpo.R
import me.hlatky.wbpo.store.PreferencesFollowedUsersStore
import me.hlatky.wbpo.dataStore
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.store.FollowedUsersStore
import javax.inject.Inject

/**
 * A fragment representing a list of [User].
 */
@AndroidEntryPoint
class UserListFragment : Fragment() {

    @Inject
    lateinit var followedUsersStore: FollowedUsersStore
    private lateinit var adapter: UserListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val avatar = "https://i.pravatar.cc/128"
        val items = listOf(
            User(
                firstName = "John",
                lastName = "Doe",
                avatar = avatar,
                id = 1,
                email = "email one"
            ),
            User(
                firstName = "Hane",
                lastName = "Doe",
                avatar = avatar,
                id = 2,
                email = "email two"
            ),
            User(
                firstName = "Hane",
                lastName = "Doe",
                avatar = avatar,
                id = 3,
                email = "email two"
            ),
            User(
                firstName = "Hane",
                lastName = "Doe",
                avatar = avatar,
                id = 4,
                email = "email two"
            ),
            User(
                firstName = "Hane",
                lastName = "Doe",
                avatar = avatar,
                id = 5,
                email = "email two"
            ),
            User(
                firstName = "Hane",
                lastName = "Doe",
                avatar = avatar,
                id = 6,
                email = "email two"
            ),
            User(
                firstName = "Jane",
                lastName = "7",
                avatar = avatar,
                id = 7,
                email = "email two"
            ),
            User(
                firstName = "John",
                lastName = "8",
                avatar = avatar,
                id = 8,
                email = "email two"
            ),
        )

        adapter = UserListRecyclerViewAdapter(
            store = PreferencesFollowedUsersStore(store = requireActivity().dataStore),
            items = items,
            lifecycleScope = lifecycleScope
        )
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
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }
}
