package com.krossovochkin.storage

import org.kodein.di.DirectDIAware

internal expect object StorageModuleFactory {

    fun createStorageImpl(directDIAware: DirectDIAware): Storage
}
