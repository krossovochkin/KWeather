package com.krossovochkin.kweather.shared.common.image

import android.content.Context
import coil.Coil
import coil.request.GetRequest

actual object ImageLoader {

    private lateinit var context: Context
    private lateinit var imageLoader: coil.ImageLoader

    fun init(context: Context) {
        this.context = context
        this.imageLoader = Coil.imageLoader(ImageLoader.context.applicationContext)
    }

    actual suspend fun load(url: String): Image {
        val request = GetRequest
            .Builder(context.applicationContext)
            .data(url)
            .build()
        val result = imageLoader
            .execute(request)
        return Image(result.drawable)
    }
}
