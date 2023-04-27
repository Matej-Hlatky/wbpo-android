package me.hlatky.wbpo.ui.user.list

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import me.hlatky.wbpo.R

/** A [MenuProvider] for [UserListFragment]. */
class UserListMenuProvider(private val onRequestLogout: () -> Unit) : MenuProvider {

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.fragment_user_list, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.action_logout -> {
            onRequestLogout(); true
        }

        else -> false
    }
}
