package me.hlatky.wbpo.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import me.hlatky.wbpo.R
import me.hlatky.wbpo.model.User

/**
 * A fragment representing a list of [User].
 */
class UserListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        val list = view.findViewById<RecyclerView>(R.id.list)

        val avatar = "https://i.pravatar.cc/128"
        val items = listOf(
            User(firstName = "John", lastName = "Doe", avatar = avatar, id = null, email = "email one"),
            User(firstName = "Hane", lastName = "Doe", avatar = avatar, id = null, email = "email two"),
        )
        list.adapter = UserListRecyclerViewAdapter(items)
        // TODO Set the LinearLayoutManager / GridLayoutManager based on screen width

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = UserListFragment()
    }
}
