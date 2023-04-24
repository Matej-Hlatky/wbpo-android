package me.hlatky.wbpo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.AndroidEntryPoint
import me.hlatky.wbpo.ui.user.login.UserLoginFragment
import me.hlatky.wbpo.ui.user.list.UserListFragment

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: ${savedInstanceState?.javaClass?.simpleName}")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.selectedRoute.observe(this) { route: Route? ->
            route?.run(::onRouteChanged)
        }
    }

    private fun onRouteChanged(route: Route) {
        Log.d(TAG, "onRouteChanged: route=${route.name}")

        val fragment = when (route) {
            Route.USER_LOGIN -> UserLoginFragment.newInstance()
            Route.USER_LIST -> UserListFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.container, fragment)
            .commitNow()
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
