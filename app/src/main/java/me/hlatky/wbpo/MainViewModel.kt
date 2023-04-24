package me.hlatky.wbpo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import me.hlatky.wbpo.repo.UserRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    userRepository: UserRepository,
) : ViewModel() {

    private val _initialRoute = if (userRepository.userSession?.token.isNullOrEmpty()) Route.USER_LOGIN else Route.USER_LIST
    private val _selectedRoute = MutableLiveData(_initialRoute)

    val selectedRoute: LiveData<Route> =
        _selectedRoute.distinctUntilChanged()

    fun selectRoute(route: Route) {
        _selectedRoute.value = route
    }
}
