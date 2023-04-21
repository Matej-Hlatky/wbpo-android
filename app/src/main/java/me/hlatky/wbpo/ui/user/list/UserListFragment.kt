package me.hlatky.wbpo.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.hlatky.wbpo.R
import me.hlatky.wbpo.model.User

/**
 * A fragment representing a list of [User].
 */
class UserListFragment : Fragment() {

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
        )

        adapter = UserListRecyclerViewAdapter(items)
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
