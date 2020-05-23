package com.krossovochkin.kweather

import android.app.Application
import com.krossovochkin.kweather.shared.common.image.ImageLoader

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ImageLoader.init(this)
    }
}