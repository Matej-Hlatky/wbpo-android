package me.hlatky.wbpo.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** [FollowedUsersStore] implementation that uses [DataStore]. */
class PreferencesFollowedUsersStore @Inject constructor(
    private val store: DataStore<Preferences>
) : FollowedUsersStore {

    override suspend fun getIsFollowed(userId: Int): Boolean {
        return store.data.map { prefs ->
            prefs[KEY]?.contains(userId.toString())
        }.firstOrNull() ?: false
    }

    override suspend fun followUser(userId: Int) {
        modifyUserFollow { it.add(userId.toString()) }
    }

    override suspend fun unFollowUser(userId: Int) {
        modifyUserFollow { it.remove(userId.toString()) }
    }

    private suspend fun modifyUserFollow(block: (set: MutableSet<String>) -> Unit) {
        store.edit { prefs ->
            val newSet = prefs[KEY].orEmpty().toMutableSet().also(block)

            prefs[KEY] = newSet
        }
    }

    companion object {
        private val KEY = stringSetPreferencesKey("followed_user_ids")
    }
}
