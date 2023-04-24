package me.hlatky.wbpo.util

import com.google.gson.Gson
import me.hlatky.wbpo.client.ApiException
import me.hlatky.wbpo.model.ApiError
import retrofit2.Response
import java.io.IOException

/** Returns the success data or throws error. */
fun <T> Response<T>.getOrThrow(): T {
    if (isSuccessful) {
        return body()!!
    }

    val errorBody = errorBody() ?: throw IOException("Response contains no data or error.")
    val error = errorBody.charStream().let { Gson().fromJson(it, ApiError::class.java) }

    throw ApiException(error)
}
