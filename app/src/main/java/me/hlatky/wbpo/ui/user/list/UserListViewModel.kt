package me.hlatky.wbpo.ui.user.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.repo.UserRepository
import me.hlatky.wbpo.store.FollowedUsersStore
import me.hlatky.wbpo.store.UserSessionStore
import javax.inject.Inject

/** [ViewModel] for the [UserListFragment] that provides list of [User]. */
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository,
    private val followedUsersStore: FollowedUsersStore,
    private val userSessionStore: UserSessionStore
) : ViewModel() {

    // TODO Use AndroidX Paging library
    private val _list = MutableStateFlow<List<User>>(emptyList())
    private var lastPage = 0

    val list: StateFlow<List<User>> = _list.asStateFlow()

    fun loadMore() {
        viewModelScope.launch {
            runCatching {
                repository.getList(lastPage + 1, 15)
            }.onSuccess {
                val prevList = _list.value

                _list.value = (prevList + it.data)
                lastPage = it.page
                //it.totalPages
            }
        }
    }

    fun updateUserFollowing(user: User, isFollowing: Boolean) {
        viewModelScope.launch {
            if (isFollowing)
                followedUsersStore.followUser(user.id)
            else
                followedUsersStore.unFollowUser(user.id)
        }
    }

    fun clearUserSession() {
        userSessionStore.value = null
    }
}
