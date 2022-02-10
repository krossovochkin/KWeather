package com.krossovochkin.kweather.weatherdetails

import com.krossovochkin.kweather.network.WeatherApi
import com.krossovochkin.kweather.network.WeatherDetailsApi
import com.krossovochkin.kweather.storagecurrentcity.currentCityStorageModule
import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsMapper
import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsMapperImpl
import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsRepositoryImpl
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetailsRepository
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModelImpl
import com.krossovochkin.kweather.weatherdetails.presentation.localization.weatherDetailsLocalizationModule
import com.krossovochkin.location.locationProviderModule
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val weatherDetailsModule = DI.Module("WeatherDetailsModule") {

    importOnce(locationProviderModule)
    import(currentCityStorageModule)
    import(weatherDetailsLocalizationModule)

    bind<WeatherDetailsApi>() with singleton {
        instance<WeatherApi>()
    }

    bind<WeatherDetailsViewModel>() with singleton {
        WeatherDetailsViewModelImpl(
            router = instance(),
            getWeatherDetailsInteractor = instance(),
            getCurrentCityInteractor = instance(),
            localizationManager = instance(),
            locationProvider = instance(),
            defaultDispatcher = Dispatchers.Default,
        )
    }

    bind<GetWeatherDetailsInteractor>() with singleton {
        GetWeatherDetailsInteractorImpl(
            weatherDetailsRepository = instance()
        )
    }

    bind<GetCurrentCityInteractor>() with singleton {
        GetCurrentCityInteractorImpl(
            currentCityStorage = instance()
        )
    }

    bind<WeatherDetailsRepository>() with singleton {
        WeatherDetailsRepositoryImpl(
            weatherDetailsApi = instance(),
            weatherDetailsMapper = instance()
        )
    }

    bind<WeatherDetailsMapper>() with singleton {
        WeatherDetailsMapperImpl()
    }
}
