package me.hlatky.wbpo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import me.hlatky.wbpo.ui.user.list.UserListFragment
import me.hlatky.wbpo.ui.user.login.UserLoginFragment

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

        // Prevent "navigation" to itself on orientation change
        // or we can store last Route into savedInstanceState
        val fragmentTag = route.name

        if (supportFragmentManager.findFragmentByTag(fragmentTag) != null)
            return

        val fragment = when (route) {
            Route.USER_LOGIN -> UserLoginFragment.newInstance()
            Route.USER_LIST -> UserListFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.container, fragment, fragmentTag)
            .commitNow()
    }

    companion object {
        private val TAG = MainActivity::class.java.name
    }
}
