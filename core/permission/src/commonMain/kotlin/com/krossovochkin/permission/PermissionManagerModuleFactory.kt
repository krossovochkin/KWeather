package com.krossovochkin.permission

import org.kodein.di.bindings.NoArgBindingDI

internal expect object PermissionManagerModuleFactory {

    fun createPermissionManagerImpl(noArgBindingDI: NoArgBindingDI<Any>): PermissionManager
}
