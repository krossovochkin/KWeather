package com.krossovochkin.kweather.shared.common.image

expect object ImageLoader {

    suspend fun load(url: String): Image
}