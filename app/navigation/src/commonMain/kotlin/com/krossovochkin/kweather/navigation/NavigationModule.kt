package com.krossovochkin.kweather.navigation

import com.krossovochkin.navigation.Router
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val navigationModule = DI.Module("NavigationModule") {

    bind<Router<RouterDestination>>() with singleton {
        NavigationModuleFactory.createRouter(this)
    }
}
