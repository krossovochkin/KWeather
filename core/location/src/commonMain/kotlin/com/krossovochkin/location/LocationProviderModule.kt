package com.krossovochkin.location

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val locationProviderModule = DI.Module("LocationProviderModule") {

    bind<LocationProvider>() with singleton {
        LocationProviderModuleFactory.createLocationProviderImpl(this)
    }
}
