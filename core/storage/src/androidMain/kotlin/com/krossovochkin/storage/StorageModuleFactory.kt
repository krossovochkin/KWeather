package com.krossovochkin.storage

import androidx.preference.PreferenceManager
import com.russhwolf.settings.SharedPreferencesSettings
import org.kodein.di.DirectDIAware
import org.kodein.di.instance

internal actual object StorageModuleFactory {

    actual fun createStorageImpl(directDIAware: DirectDIAware): Storage {
        return StorageAdapter(
            settings = SharedPreferencesSettings(
                delegate = PreferenceManager.getDefaultSharedPreferences(directDIAware.instance()),
                commit = true
            )
        )
    }
}
