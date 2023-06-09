package me.hlatky.wbpo.store

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import me.hlatky.wbpo.model.UserSession
import me.hlatky.wbpo.util.getEncryptedSharedPreferences
import javax.inject.Inject

/** [UserSessionStore] build using [SharedPreferences]. */
class PreferencesUserSessionStore @Inject constructor(
    @ApplicationContext context: Context
) : UserSessionStore {

    private val sp: SharedPreferences = context.getEncryptedSharedPreferences("user_session")

    override var value: UserSession?
        get() {
            val text = sp.getString(KEY_USER_SESSION, null)

            return if (text.isNullOrEmpty()) null else SERIALIZER.fromJson(
                text,
                UserSession::class.java
            )
        }
        set(value) {
            sp.edit {
                putString(KEY_USER_SESSION, value.run(SERIALIZER::toJson))
            }
        }

    companion object {
        private val SERIALIZER: Gson = GsonBuilder().create()
        private const val KEY_USER_SESSION = "user_session"
    }
}
