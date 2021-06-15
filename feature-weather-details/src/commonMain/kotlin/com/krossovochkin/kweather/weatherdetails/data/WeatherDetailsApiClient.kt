package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.domain.CityId
import io.ktor.client.*
import io.ktor.client.request.*

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather"

internal class WeatherDetailsApiClient(
    private val client: HttpClient,
    private val apiKey: String
) : WeatherDetailsApi {

    override suspend fun getWeatherDetails(cityId: CityId): WeatherDetailsDto {
        return client.get("$BASE_URL?id=${cityId.id}&appid=$apiKey&units=metric")
    }
}
