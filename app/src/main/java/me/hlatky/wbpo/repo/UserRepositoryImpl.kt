package me.hlatky.wbpo.repo

import me.hlatky.wbpo.client.ApiClient
import me.hlatky.wbpo.model.UserLoginRequest
import me.hlatky.wbpo.model.UserRegisterRequest
import me.hlatky.wbpo.model.User
import retrofit2.await
import javax.inject.Inject

/** [UserRepository] that uses [ApiClient]. */
class UserRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
) : UserRepository {

    override suspend fun register(email: String, password: String): String = apiClient
        .register(UserRegisterRequest(username = email, email = email, password = password))
        .await().token

    override suspend fun login(email: String, password: String): String = apiClient
        .login(UserLoginRequest(username = email, email = email, password = password))
        .await().token
    //         val response = repository.login(email, password)
    //        // TODO Store access TOKEN

    override suspend fun logout() =
        apiClient.logout().await()

    override suspend fun getAll(page: Int, perPage: Int): List<User> =
        apiClient.getUsers(perPage, page).await().data
}
