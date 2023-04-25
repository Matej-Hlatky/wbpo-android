package me.hlatky.wbpo.ui.user.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.repo.UserListPagingSource
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

    /** Contains paging for list of [User]. */
    val list = Pager(PagingConfig(initialLoadSize = 4, pageSize = 4)) {
        UserListPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

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
