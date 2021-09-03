package com.krossovochkin.kweather.weathermap

import com.krossovochkin.kweather.network.DI_TAG_API_KEY
import com.krossovochkin.kweather.network.DI_TAG_MAPBOX_API_KEY
import com.krossovochkin.kweather.storagecurrentcity.currentCityStorageModule
import com.krossovochkin.kweather.weathermap.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.weathermap.domain.GetCurrentCityInteractorImpl
import com.krossovochkin.kweather.weathermap.domain.GetWeatherMapDataInteractor
import com.krossovochkin.kweather.weathermap.domain.GetWeatherMapDataInteractorImpl
import com.krossovochkin.kweather.weathermap.presentation.WeatherMapViewModel
import com.krossovochkin.kweather.weathermap.presentation.WeatherMapViewModelImpl
import com.krossovochkin.kweather.weathermap.presentation.localization.weatherMapLocalizationModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val weatherMapModule = DI.Module("WeatherMapModule") {

    importOnce(currentCityStorageModule)
    import(weatherMapLocalizationModule)

    bind<WeatherMapViewModel>() with singleton {
        WeatherMapViewModelImpl(
            getCurrentCityInteractor = instance(),
            localizationManager = instance(),
            getWeatherMapDataInteractor = instance(),
            router = instance(),
        )
    }

    bind<GetWeatherMapDataInteractor>() with singleton {
        GetWeatherMapDataInteractorImpl(
            mapboxApiKey = instance(tag = DI_TAG_MAPBOX_API_KEY),
            openWeatherMapApiKey = instance(tag = DI_TAG_API_KEY)
        )
    }

    bind<GetCurrentCityInteractor>() with singleton {
        GetCurrentCityInteractorImpl(
            currentCityStorage = instance()
        )
    }
}
