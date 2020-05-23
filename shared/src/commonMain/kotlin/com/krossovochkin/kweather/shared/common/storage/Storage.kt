package com.krossovochkin.kweather.shared.common.storage

interface Storage {

    suspend fun getInt(key: String): Int?

    suspend fun putInt(key: String, value: Int)

    suspend fun getString(key: String): String?

    suspend fun putString(key: String, value: String)

    suspend fun remove(key: String)
}

expect class StorageImpl : Storage
