package com.krossovochkin.kweather.network

import com.krossovochkin.kweather.network.dto.CityListDto
import com.krossovochkin.kweather.network.dto.WeatherDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private const val BASE_URL = "https://api.openweathermap.org/data"
private const val UNITS = "metric"

class WeatherApiClient(
    private val client: HttpClient,
    private val apiKey: String
) : WeatherApi {

    override suspend fun getCityList(query: String): CityListDto {
        return client
            .get("$BASE_URL/2.5/find?q=$query&appid=$apiKey&units=$UNITS")
            .body()
    }

    override suspend fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    ): WeatherDetailsDto {
        return client
            .get("$BASE_URL/3.0/onecall?lat=$latitude&lon=$longitude&appid=$apiKey&units=$UNITS")
            .body()
    }
}
