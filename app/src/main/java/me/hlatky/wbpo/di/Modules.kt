package me.hlatky.wbpo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.hlatky.wbpo.BuildConfig
import me.hlatky.wbpo.client.ApiClient
import me.hlatky.wbpo.client.ApiClientFactory
import me.hlatky.wbpo.repo.UserRepository
import me.hlatky.wbpo.repo.UserRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ClientModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient =
        ApiClientFactory.create(BuildConfig.API_ENDPOINT, BuildConfig.DEBUG)
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideApiClient(apiClient: ApiClient): UserRepository =
        UserRepositoryImpl(apiClient)
}
