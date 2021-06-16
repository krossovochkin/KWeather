package com.krossovochkin.kweather

import android.content.Context
import com.krossovochkin.kweather.weatherdetails.DI_TAG_API_KEY
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun appModule(applicationContext: Context) = DI.Module("AppModule") {
    bind<Context>() with singleton { applicationContext }
    bind<String>(tag = DI_TAG_API_KEY) with singleton { BuildConfig.API_KEY }
}
