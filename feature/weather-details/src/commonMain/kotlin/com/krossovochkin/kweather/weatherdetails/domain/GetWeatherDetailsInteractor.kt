package com.krossovochkin.kweather.weatherdetails.domain

import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails

interface GetWeatherDetailsInteractor {

    suspend fun get(city: City): WeatherDetails
}

internal class GetWeatherDetailsInteractorImpl(
    private val weatherDetailsRepository: WeatherDetailsRepository
) : GetWeatherDetailsInteractor {

    override suspend fun get(city: City): WeatherDetails {
        return weatherDetailsRepository.getWeatherDetails(city)
    }
}
