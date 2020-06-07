package com.krossovochkin.kweather.shared.common.image

@Suppress("UnusedPrivateMember")
expect object ImageLoader {

    suspend fun load(url: String): Image
}
