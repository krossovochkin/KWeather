package com.krossovochkin.storage

import com.russhwolf.settings.StorageSettings
import org.kodein.di.DirectDIAware

internal actual object StorageModuleFactory {

    actual fun createStorageImpl(directDIAware: DirectDIAware): Storage {
        return StorageAdapter(
            settings = StorageSettings()
        )
    }
}
