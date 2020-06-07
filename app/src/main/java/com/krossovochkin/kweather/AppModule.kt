package com.krossovochkin.kweather

import android.content.Context
import com.krossovochkin.kweather.shared.common.image.ImageLoader
import com.krossovochkin.kweather.shared.common.localization.LocalizationManager
import com.krossovochkin.kweather.shared.common.localization.LocalizationManagerImpl
import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.common.router.RouterImpl
import com.krossovochkin.kweather.shared.feature.weatherdetails.DI_TAG_API_KEY
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun appModule(applicationContext: Context) = DI.Module("AppModule") {
    bind<Context>() with singleton { applicationContext }
    bind<Router>() with singleton { RouterImpl() }
    bind<ImageLoader>() with singleton { ImageLoader }
    bind<LocalizationManager>() with singleton {
        LocalizationManagerImpl(instance())
    }
    bind<String>(tag = DI_TAG_API_KEY) with singleton { BuildConfig.API_KEY }
}
