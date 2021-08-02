package com.krossovochkin.permission

import org.kodein.di.bindings.NoArgBindingDI
import org.kodein.di.instance

internal actual object PermissionManagerModuleFactory {

    actual fun createPermissionManagerImpl(noArgBindingDI: NoArgBindingDI<Any>): PermissionManager {
        return PermissionManagerImpl(
            lifecycle = noArgBindingDI.instance()
        )
    }
}
