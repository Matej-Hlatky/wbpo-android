package me.hlatky.wbpo.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PreferencesFollowedUsersStore(
    private val store: DataStore<Preferences>
) : FollowedUsersStore {

    override suspend fun getIsFollowed(userId: Int): Boolean {
        return store.data.map { prefs ->
            prefs[KEY]?.contains(userId.toString()) ?: false
        }.first()
    }

    override suspend fun followUser(userId: Int) {
        store.edit { prefs ->
            // TODO Extract into private fun with block
            val newSet = prefs[KEY].orEmpty().toMutableSet().also {
                it.add(userId.toString())
            }

            prefs[KEY] = newSet
        }
    }

    override suspend fun unFollowUser(userId: Int) {
        store.edit { prefs ->
            val newSet = prefs[KEY].orEmpty().toMutableSet().also {
                it.remove(userId.toString())
            }

            prefs[KEY] = newSet
        }
    }


    companion object {
        private val KEY = stringSetPreferencesKey("followed_user_ids")
    }
}
