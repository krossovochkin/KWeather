package com.krossovochkin.kweather.service.storagecurrentcity

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val currentCityIdStorageModule = DI.Module("CurrentCityIdModule") {

    bind<MutableCurrentCityIdStorage>() with singleton {
        CurrentCityIdStorageImpl(
            storage = instance()
        )
    }

    bind<CurrentCityIdStorage>() with singleton {
        instance<MutableCurrentCityIdStorage>()
    }
}
