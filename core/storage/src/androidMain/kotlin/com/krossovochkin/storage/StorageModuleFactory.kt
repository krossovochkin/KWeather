package com.krossovochkin.storage

import org.kodein.di.DirectDIAware
import org.kodein.di.instance

internal actual object StorageModuleFactory {

    actual fun createStorageImpl(directDIAware: DirectDIAware): Storage {
        return StorageImpl(
            context = directDIAware.instance()
        )
    }
}
