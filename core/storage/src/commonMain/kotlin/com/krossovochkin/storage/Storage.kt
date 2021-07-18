package com.krossovochkin.storage

interface Storage {

    suspend fun getInt(key: String): Int?

    suspend fun putInt(key: String, value: Int)

    suspend fun getString(key: String): String?

    suspend fun putString(key: String, value: String)

    suspend fun getDouble(key: String): Double?

    suspend fun putDouble(key: String, value: Double)

    suspend fun remove(key: String)
}
