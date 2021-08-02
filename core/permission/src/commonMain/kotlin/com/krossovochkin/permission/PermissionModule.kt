package com.krossovochkin.permission

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val permissionModule = DI.Module("PermissionModule") {

    bind<PermissionManager>() with singleton {
        PermissionManagerModuleFactory.createPermissionManagerImpl(this)
    }
}
