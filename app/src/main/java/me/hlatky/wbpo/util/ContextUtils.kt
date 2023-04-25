package me.hlatky.wbpo.util

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

fun Context.getEncryptedSharedPreferences(fileName: String): SharedPreferences =
    EncryptedSharedPreferences.create(
        fileName,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        this,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

inline fun <reified T> Context.provideService(): T =
    getSystemService(T::class.java)
