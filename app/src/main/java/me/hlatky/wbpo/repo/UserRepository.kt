package me.hlatky.wbpo.repo

import me.hlatky.wbpo.model.User
import me.hlatky.wbpo.model.UserSession

/** Repo for [User]. */
interface UserRepository {

    val userSession: UserSession?

    suspend fun register(email: String, password: String)

    suspend fun login(email: String, password: String)

    suspend fun logout()

    // TODO Return GetUsersResponse?
    suspend fun getAll(page: Int, perPage: Int): List<User>
}
