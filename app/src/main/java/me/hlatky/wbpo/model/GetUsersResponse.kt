package me.hlatky.wbpo.model

import com.google.gson.annotations.SerializedName

data class GetUsersResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<User>,
)
