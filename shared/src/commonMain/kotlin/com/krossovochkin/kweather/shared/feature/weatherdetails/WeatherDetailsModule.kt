package com.krossovochkin.kweather.shared.feature.weatherdetails

import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapper
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListMapperImpl
import com.krossovochkin.kweather.shared.feature.weatherdetails.data.*
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.*
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsViewModelImpl
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
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
            imageLoader = instance(),
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
                    json = Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true))
                )
            }
        }
    }
}
