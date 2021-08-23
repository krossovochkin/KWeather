package com.krossovochkin.kweather.storagecurrentcity

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val currentCityStorageModule = DI.Module("CurrentCityModule") {

    bind<MutableCurrentCityStorage>() with singleton {
        CurrentCityStorageImpl(
            storage = instance()
        )
    }

    bind<CurrentCityStorage>() with singleton {
        instance<MutableCurrentCityStorage>()
    }
}
