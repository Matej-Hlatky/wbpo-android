package me.hlatky.wbpo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.hlatky.wbpo.BuildConfig
import me.hlatky.wbpo.client.ApiClient
import me.hlatky.wbpo.client.ApiClientFactory
import me.hlatky.wbpo.dataStore
import me.hlatky.wbpo.repo.UserRepository
import me.hlatky.wbpo.repo.UserRepositoryImpl
import me.hlatky.wbpo.store.FollowedUsersStore
import me.hlatky.wbpo.store.PreferencesFollowedUsersStore
import me.hlatky.wbpo.store.PreferencesUserSessionStore
import me.hlatky.wbpo.store.UserSessionStore
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
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {

    @Binds
    @Singleton
    abstract fun bindFollowedUsersStore(impl: PreferencesFollowedUsersStore): FollowedUsersStore

    @Binds
    @Singleton
    abstract fun bindUserSessionStore(impl: PreferencesUserSessionStore): UserSessionStore
}
