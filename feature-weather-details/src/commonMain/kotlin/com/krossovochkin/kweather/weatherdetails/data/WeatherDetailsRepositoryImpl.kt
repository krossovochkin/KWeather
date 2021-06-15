package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.domain.City
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetails
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetailsRepository

class WeatherDetailsRepositoryImpl(
    private val weatherDetailsApi: WeatherDetailsApi,
    private val weatherDetailsMapper: WeatherDetailsMapper
) : WeatherDetailsRepository {

    override suspend fun getWeatherDetails(city: City): WeatherDetails {
        return weatherDetailsApi.getWeatherDetails(city.id)
            .let { dto -> weatherDetailsMapper.map(city, dto) }
    }
}
