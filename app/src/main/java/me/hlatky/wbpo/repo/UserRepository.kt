package me.hlatky.wbpo.repo

import me.hlatky.wbpo.model.User

/** Repo for [User]. */
interface UserRepository {

    suspend fun register(email: String, password: String): String

    suspend fun login(email: String, password: String): String

    suspend fun logout()

    // TODO Return GetUsersResponse?
    suspend fun getAll(page: Int, perPage: Int): List<User>
}
