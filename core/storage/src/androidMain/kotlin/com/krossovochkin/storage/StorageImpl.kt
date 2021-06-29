package com.krossovochkin.storage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE

private const val KEY_PREFS = "prefs"

@SuppressLint("ApplySharedPref")
internal actual class StorageImpl(
    context: Context
) : Storage {

    private val prefs = context.getSharedPreferences(KEY_PREFS, MODE_PRIVATE)

    override suspend fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    override suspend fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).commit()
    }

    override suspend fun getInt(key: String): Int? {
        return if (prefs.contains(key)) {
            prefs.getInt(key, 0)
        } else {
            null
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).commit()
    }

    override suspend fun remove(key: String) {
        prefs.edit().remove(key).commit()
    }
}
