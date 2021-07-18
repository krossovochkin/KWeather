package com.krossovochkin.storage

import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toSuspendSettings

internal class StorageAdapter(
    settings: Settings
) : Storage {

    private val suspendSettings = settings.toSuspendSettings()

    override suspend fun getInt(key: String): Int? {
        return suspendSettings.getIntOrNull(key)
    }

    override suspend fun putInt(key: String, value: Int) {
        return suspendSettings.putInt(key, value)
    }

    override suspend fun getString(key: String): String? {
        return suspendSettings.getStringOrNull(key)
    }

    override suspend fun putString(key: String, value: String) {
        return suspendSettings.putString(key, value)
    }

    override suspend fun getDouble(key: String): Double? {
        return suspendSettings.getDoubleOrNull(key)
    }

    override suspend fun putDouble(key: String, value: Double) {
        return suspendSettings.putDouble(key, value)
    }

    override suspend fun remove(key: String) {
        return suspendSettings.remove(key)
    }
}
