package com.krossovochkin.kweather.network

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import kotlinx.serialization.json.Json
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

const val DI_TAG_API_KEY = "DI_TAG_API_KEY"

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
