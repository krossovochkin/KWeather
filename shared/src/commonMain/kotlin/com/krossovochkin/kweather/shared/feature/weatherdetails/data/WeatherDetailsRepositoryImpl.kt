package com.krossovochkin.kweather.shared.feature.weatherdetails.data

import com.krossovochkin.kweather.shared.common.domain.CityId
import com.krossovochkin.kweather.shared.feature.citylist.domain.City
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.WeatherDetails
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.WeatherDetailsRepository

class WeatherDetailsRepositoryImpl(
    private val weatherDetailsApi: WeatherDetailsApi,
    private val weatherDetailsMapper: WeatherDetailsMapper
) : WeatherDetailsRepository {

    override suspend fun getWeatherDetails(city: City): WeatherDetails {
        return weatherDetailsApi.getWeatherDetails(city.id)
            .let { dto -> weatherDetailsMapper.map(city, dto) }
    }
}