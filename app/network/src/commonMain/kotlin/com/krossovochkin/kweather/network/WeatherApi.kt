package com.krossovochkin.kweather.network

import com.krossovochkin.kweather.network.dto.CityListDto
import com.krossovochkin.kweather.network.dto.WeatherDetailsDto

interface WeatherApi :
    CityListApi,
    WeatherDetailsApi

interface CityListApi {

    suspend fun getCityList(query: String): CityListDto
}

interface WeatherDetailsApi {

    suspend fun getWeatherDetails(
        latitude: Double,
        longitude: Double
    ): WeatherDetailsDto
}
