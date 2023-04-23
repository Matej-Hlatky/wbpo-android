package me.hlatky.wbpo.client

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor.Level as LoggingLevel

object ApiClientFactory {

    /**
     * Creates HTTP REST client for [ApiClient].
     *
     * @param endpoint The service endpoint base URL address, like "https://api.com" without ending '/'.
     * @param isDebug Debug flag - affects logging level.
     *
     * @return The client.
     */
    fun create(endpoint: String, isDebug: Boolean): ApiClient {
        val loggingLevel = if (isDebug) LoggingLevel.BODY else LoggingLevel.NONE
        val client = createClient(loggingLevel)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(endpoint)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(JSON_CONVERTER))
            .build()

        return retrofit.create(ApiClient::class.java)
    }

    private fun createClient(loggingLevel: LoggingLevel): OkHttpClient {
        return OkHttpClient.Builder().also {
            it.connectTimeout(5, TimeUnit.SECONDS)
            it.readTimeout(30, TimeUnit.SECONDS)

            // This interceptor logs HTTP traffic
            it.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = loggingLevel
                },
            )
        }.build()
    }

    private val JSON_CONVERTER: Gson = GsonBuilder().create()
}
