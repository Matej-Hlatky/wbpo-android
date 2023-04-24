package me.hlatky.wbpo.client

import me.hlatky.wbpo.model.GetUsersResponse
import me.hlatky.wbpo.model.UserLoginRequest
import me.hlatky.wbpo.model.UserLoginResponse
import me.hlatky.wbpo.model.UserRegisterRequest
import me.hlatky.wbpo.model.UserRegisterResponse
import me.hlatky.wbpo.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * ReqRes API.
 *
 * See [https://reqres.in/api-docs/](https://reqres.in/api-docs/).
 */
interface ApiClient {
    @POST("register")
    fun register(@Body request: UserRegisterRequest): Call<UserRegisterResponse>

    @POST("login")
    fun login(@Body request: UserLoginRequest): Call<UserLoginResponse>

    @POST("logout")
    fun logout(): Call<Unit>

    @GET("users")
    fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Call<GetUsersResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>
}
