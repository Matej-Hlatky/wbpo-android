package me.hlatky.wbpo.repo

import me.hlatky.wbpo.client.ApiClient
import me.hlatky.wbpo.model.GetUsersResponse
import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.model.UserLoginRequest
import me.hlatky.wbpo.model.UserRegisterRequest
import me.hlatky.wbpo.model.UserSession
import me.hlatky.wbpo.store.FollowedUsersStore
import me.hlatky.wbpo.store.UserSessionStore
import me.hlatky.wbpo.util.getOrThrow
import retrofit2.awaitResponse
import javax.inject.Inject

/** [UserRepository] that uses [ApiClient]. */
class UserRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient,
    private val userSessionStore: UserSessionStore,
    private val followedUsersStore: FollowedUsersStore,
) : UserRepository {

    override var userSession: UserSession? by userSessionStore::value

    override suspend fun register(email: String, password: String) {
        apiClient
            .register(UserRegisterRequest(username = email, email = email, password = password))
            .awaitResponse()
            .getOrThrow()
            .also {
                userSession = UserSession(userId = it.id, token = it.token)
            }
    }

    override suspend fun login(email: String, password: String) {
        apiClient
            .login(UserLoginRequest(username = email, email = email, password = password))
            .awaitResponse()
            .getOrThrow()
            .also {
                userSession = UserSession(token = it.token)
            }
    }

    override suspend fun logout() {
        apiClient.logout().awaitResponse().getOrThrow().also {
            userSession = null
        }
    }

    override suspend fun getList(page: Int, perPage: Int): GetUsersResponse {
        val result = apiClient
            .getUsers(page, perPage)
            .awaitResponse()
            .getOrThrow()

        return result.copy(
            // Map following flag values
            data = result.data.map { user ->
                user.copy(isFollowed = followedUsersStore.getIsFollowed(user.id))
            }
        )
    }

    override suspend fun updateUserFollowing(user: User, isFollowing: Boolean) {
        if (isFollowing)
            followedUsersStore.followUser(user.id)
        else
            followedUsersStore.unFollowUser(user.id)
    }
}
