package com.krossovochkin.navigation

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val navigationModule = DI.Module("NavigationModule") {

    bind<Router>() with singleton {
        NavigationModuleFactory.createRouter(this)
    }
}
