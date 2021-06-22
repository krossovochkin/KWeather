package com.krossovochkin.kweather.core

import com.krossovochkin.kweather.core.dto.CityListDto
import com.krossovochkin.kweather.core.dto.WeatherDetailsDto

interface WeatherApi :
    CityListApi,
    WeatherDetailsApi

interface CityListApi {

    suspend fun getCityList(query: String): CityListDto
}

interface WeatherDetailsApi {

    suspend fun getWeatherDetails(cityId: Int): WeatherDetailsDto
}
