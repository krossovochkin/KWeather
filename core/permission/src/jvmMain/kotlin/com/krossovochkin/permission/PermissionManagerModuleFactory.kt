package com.krossovochkin.permission

import org.kodein.di.bindings.NoArgBindingDI

internal actual object PermissionManagerModuleFactory {

    actual fun createPermissionManagerImpl(noArgBindingDI: NoArgBindingDI<Any>): PermissionManager {
        return PermissionManagerImpl()
    }
}
