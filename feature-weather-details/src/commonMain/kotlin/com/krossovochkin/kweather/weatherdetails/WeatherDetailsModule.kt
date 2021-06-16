package com.krossovochkin.kweather.weatherdetails

import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsApi
import com.krossovochkin.kweather.weatherdetails.data.WeatherDetailsApiClient
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
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json
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
            getCurrentCityInteractor = instance(),
            localizationManager = instance()
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

    bind<WeatherDetailsApi>() with singleton {
        WeatherDetailsApiClient(
            client = instance(),
            apiKey = instance(tag = DI_TAG_API_KEY)
        )
    }

    bind<WeatherDetailsMapper>() with singleton {
        WeatherDetailsMapperImpl()
    }

    bind<HttpClient>() with singleton {
        HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    json = Json {
                        this.ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}
