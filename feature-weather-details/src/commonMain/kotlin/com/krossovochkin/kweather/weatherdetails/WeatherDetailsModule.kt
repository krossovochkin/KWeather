package com.krossovochkin.kweather.weatherdetails

import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsMapper
import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsMapperImpl
import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsRepositoryImpl
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityIdInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetCurrentCityIdInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractor
import com.krossovochkin.kweather.weatherdetails.domain.GetWeatherDetailsInteractorImpl
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetailsRepository
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModelImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

const val DI_TAG_API_KEY = "DI_TAG_API_KEY"

val weatherDetailsModule = DI.Module("WeatherDetailsModule") {

    bind<WeatherDetailsViewModel>() with singleton {
        WeatherDetailsViewModelImpl(
            router = instance(),
            getWeatherDetailsInteractor = instance(),
            getCurrentCityIdInteractor = instance(),
            localizationManager = instance()
        )
    }

    bind<GetWeatherDetailsInteractor>() with singleton {
        GetWeatherDetailsInteractorImpl(
            weatherDetailsRepository = instance()
        )
    }

    bind<GetCurrentCityIdInteractor>() with singleton {
        GetCurrentCityIdInteractorImpl(
            currentCityIdStorage = instance()
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
