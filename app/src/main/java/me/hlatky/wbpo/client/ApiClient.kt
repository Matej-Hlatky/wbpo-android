package me.hlatky.wbpo.client

import me.hlatky.wbpo.model.GetUsersResponse
import me.hlatky.wbpo.model.LoginRequest
import me.hlatky.wbpo.model.LoginResponse
import me.hlatky.wbpo.model.RegisterRequest
import me.hlatky.wbpo.model.RegisterResponse
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
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("logout")
    fun logout(): Call<Void>

    @GET("users")
    fun getUsers(@Query("perPage") perPage: Int, @Query("page") page: Int): Call<GetUsersResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>
}
