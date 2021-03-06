package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.WeatherDetailsApi
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetails
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetailsRepository

internal class WeatherDetailsRepositoryImpl(
    private val weatherDetailsApi: WeatherDetailsApi,
    private val weatherDetailsMapper: WeatherDetailsMapper
) : WeatherDetailsRepository {

    override suspend fun getWeatherDetails(cityId: CityId): WeatherDetails {
        return weatherDetailsApi.getWeatherDetails(cityId.id)
            .let { dto -> weatherDetailsMapper.map(dto) }
    }
}
