package com.krossovochkin.kweather.shared.feature.weatherdetails.data

import com.krossovochkin.kweather.shared.common.domain.CityId

interface WeatherDetailsApi {

    suspend fun getWeatherDetails(cityId: CityId): WeatherDetailsDto
}