package me.hlatky.wbpo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged

class MainViewModel : ViewModel() {

    // TODO Determine it - check if is logged-in or have token
    private val _selectedRoute = MutableLiveData(Route.USER_LOGIN)

    val selectedRoute: LiveData<Route> =
        _selectedRoute.distinctUntilChanged()

    fun selectRoute(route: Route) {
        _selectedRoute.value = route
    }
}
