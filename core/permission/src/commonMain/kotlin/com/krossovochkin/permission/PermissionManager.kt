package com.krossovochkin.permission

interface PermissionManager {

    suspend fun init()

    suspend fun requestPermission(permission: Permission): Boolean
}

enum class Permission {
    COARSE_LOCATION
}
