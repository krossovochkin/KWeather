package com.krossovochkin.storage

import androidx.preference.PreferenceManager
import com.russhwolf.settings.AndroidSettings
import org.kodein.di.DirectDIAware
import org.kodein.di.instance

internal actual object StorageModuleFactory {

    actual fun createStorageImpl(directDIAware: DirectDIAware): Storage {
        return StorageAdapter(
            settings = AndroidSettings(
                delegate = PreferenceManager.getDefaultSharedPreferences(directDIAware.instance()),
                commit = true
            )
        )
    }
}
