package com.krossovochkin.kweather.core

import com.krossovochkin.kweather.core.dto.CityListDto
import com.krossovochkin.kweather.core.dto.WeatherDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val BASE_URL = "https://api.openweathermap.org/data/2.5"
private const val UNITS = "metric"

class WeatherApiClient(
    private val client: HttpClient,
    private val apiKey: String
) : WeatherApi {

    override suspend fun getCityList(query: String): CityListDto {
        return client.get("$BASE_URL/find?q=${query}&appid=$apiKey&units=$UNITS")
    }

    override suspend fun getWeatherDetails(cityId: Int): WeatherDetailsDto {
        return client.get("$BASE_URL/weather?id=${cityId}&appid=$apiKey&units=$UNITS")
    }
}
