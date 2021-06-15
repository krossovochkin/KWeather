package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.core.domain.City

interface GetWeatherDetailsInteractor {

    suspend fun get(city: City): WeatherDetails
}

class GetWeatherDetailsInteractorImpl(
    private val weatherDetailsRepository: WeatherDetailsRepository
) : GetWeatherDetailsInteractor {

    override suspend fun get(city: City): WeatherDetails {
        return weatherDetailsRepository.getWeatherDetails(city)
    }
}
