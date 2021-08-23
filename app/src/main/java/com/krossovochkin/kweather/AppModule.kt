package com.krossovochkin.kweather

import android.app.Application
import android.content.Context
import com.krossovochkin.kweather.network.DI_TAG_API_KEY
import com.krossovochkin.lifecycle.lifecycleModule
import com.krossovochkin.permission.permissionModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

fun appModule(applicationContext: Context) = DI.Module("AppModule") {
    bind<Context>() with singleton { applicationContext }
    bind<Application>() with singleton { applicationContext as Application }
    bind<String>(tag = DI_TAG_API_KEY) with singleton { BuildConfig.API_KEY }

    import(lifecycleModule)
    import(permissionModule)
}
