package com.krossovochkin.kweather.core.image

@Suppress("UnusedPrivateMember")
expect object ImageLoader {

    suspend fun load(url: String): Image
}
