package com.krossovochkin.storage

import com.russhwolf.settings.JvmPreferencesSettings
import org.kodein.di.DirectDIAware
import java.util.prefs.Preferences

internal actual object StorageModuleFactory {

    actual fun createStorageImpl(directDIAware: DirectDIAware): Storage {
        return StorageAdapter(
            settings = JvmPreferencesSettings(Preferences.userRoot())
        )
    }
}
