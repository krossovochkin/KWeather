package com.krossovochkin.kweather

import android.app.Application
import com.krossovochkin.kweather.shared.common.image.ImageLoader
import com.krossovochkin.kweather.shared.common.storage.storageModule
import org.kodein.di.DI
import org.kodein.di.DIAware

class App : Application(), DIAware {

    override val di: DI = DI.lazy {
        import(appModule(this@App))
        import(storageModule)
    }

    override fun onCreate() {
        super.onCreate()
        ImageLoader.init(this)
    }
}
