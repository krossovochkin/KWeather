package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.WeatherDetailsApi
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetailsRepository

internal class WeatherDetailsRepositoryImpl(
    private val weatherDetailsApi: WeatherDetailsApi,
    private val weatherDetailsMapper: WeatherDetailsMapper
) : WeatherDetailsRepository {

    override suspend fun getWeatherDetails(city: City): WeatherDetails {
        return weatherDetailsApi
            .getWeatherDetails(
                latitude = city.location.latitude,
                longitude = city.location.longitude
            )
            .let { dto -> weatherDetailsMapper.map(city, dto) }
    }
}
