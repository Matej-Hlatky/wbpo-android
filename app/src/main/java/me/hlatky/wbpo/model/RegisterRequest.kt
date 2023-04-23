package me.hlatky.wbpo.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)
