package com.krossovochkin.storage

import com.russhwolf.settings.PreferencesSettings
import org.kodein.di.DirectDIAware
import java.util.prefs.Preferences

internal actual object StorageModuleFactory {

    actual fun createStorageImpl(directDIAware: DirectDIAware): Storage {
        return StorageAdapter(
            settings = PreferencesSettings(Preferences.userRoot())
        )
    }
}
