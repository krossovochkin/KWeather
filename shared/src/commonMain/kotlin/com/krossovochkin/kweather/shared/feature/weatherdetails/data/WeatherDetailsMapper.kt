package com.krossovochkin.kweather.shared.feature.weatherdetails.data

import com.krossovochkin.kweather.shared.feature.citylist.domain.City
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.WeatherDetails

interface WeatherDetailsMapper {

    fun map(
        city: City,
        dto: WeatherDetailsDto
    ): WeatherDetails
}

private const val WEATHER_CONDITIONS_IMAGE_URL = "http://openweathermap.org/img/wn/"

class WeatherDetailsMapperImpl : WeatherDetailsMapper {

    override fun map(
        city: City,
        dto: WeatherDetailsDto
    ): WeatherDetails {
        return with(dto) {
            WeatherDetails(
                city = city,
                temperature = main.temp,
                weatherConditionsImageUrl = "$WEATHER_CONDITIONS_IMAGE_URL${weather.first().icon}@2x.png"
            )
        }
    }
}