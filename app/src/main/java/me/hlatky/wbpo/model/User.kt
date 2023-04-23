package me.hlatky.wbpo.model

// TODO JSON attr mapping
data class User(
    val id: Int?,
    val email: String?,
    val firstName: String?, // first_name
    val lastName: String?, // last_name
    val avatar: String?, // last_name
) {
    val fullName: String =
        listOf(firstName, "", lastName).joinToString(separator = " ")
}
