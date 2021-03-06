package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.CityId

interface GetWeatherDetailsInteractor {

    suspend fun get(cityId: CityId): WeatherDetails
}

internal class GetWeatherDetailsInteractorImpl(
    private val weatherDetailsRepository: WeatherDetailsRepository
) : GetWeatherDetailsInteractor {

    override suspend fun get(cityId: CityId): WeatherDetails {
        return weatherDetailsRepository.getWeatherDetails(cityId)
    }
}
