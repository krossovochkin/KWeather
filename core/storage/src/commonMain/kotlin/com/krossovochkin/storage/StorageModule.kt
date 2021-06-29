package com.krossovochkin.storage

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val storageModule = DI.Module("StorageModule") {

    bind<Storage>() with singleton {
        StorageModuleFactory.createStorageImpl(this)
    }
}
