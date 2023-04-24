package me.hlatky.wbpo.model

data class UserRegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
