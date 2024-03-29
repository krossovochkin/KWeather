package com.krossovochkin.kweather.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

const val DI_TAG_API_KEY = "DI_TAG_API_KEY"
const val DI_TAG_MAPBOX_API_KEY = "DI_TAG_MAPBOX_API_KEY"

val networkModule = DI.Module("WeatherApiModule") {

    bind<CityListApi>() with singleton {
        instance<WeatherApi>()
    }

    bind<WeatherApi>() with singleton {
        WeatherApiClient(
            client = instance(),
            apiKey = instance(tag = DI_TAG_API_KEY)
        )
    }

    bind<HttpClient>() with singleton {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}
