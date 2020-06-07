package com.krossovochkin.kweather.shared.feature.weatherdetails.domain

import com.krossovochkin.kweather.shared.feature.citylist.domain.City

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
