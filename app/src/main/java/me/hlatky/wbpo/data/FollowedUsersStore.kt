package me.hlatky.wbpo.data

interface FollowedUsersStore {

    suspend fun getIsFollowed(userId: Int): Boolean

    suspend fun followUser(userId: Int)

    suspend fun unFollowUser(userId: Int)
}
