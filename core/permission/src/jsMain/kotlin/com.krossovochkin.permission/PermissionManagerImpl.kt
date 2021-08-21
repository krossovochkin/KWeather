package com.krossovochkin.permission

internal actual class PermissionManagerImpl : PermissionManager {

    override suspend fun init() = Unit

    override suspend fun requestPermission(permission: Permission): Boolean {
        return false
    }
}
