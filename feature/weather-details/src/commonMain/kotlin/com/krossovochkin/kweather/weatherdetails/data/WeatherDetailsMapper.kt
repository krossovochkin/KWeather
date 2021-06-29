package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.dto.WeatherDetailsDto
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetails

internal interface WeatherDetailsMapper {

    fun map(dto: WeatherDetailsDto): WeatherDetails
}

private const val WEATHER_CONDITIONS_IMAGE_URL = "https://openweathermap.org/img/wn/"

internal class WeatherDetailsMapperImpl : WeatherDetailsMapper {

    override fun map(dto: WeatherDetailsDto): WeatherDetails {
        return with(dto) {
            WeatherDetails(
                city = City(
                    id = CityId(dto.id),
                    name = dto.name
                ),
                temperature = main.temp.toInt(),
                weatherConditionsImageUrl = "$WEATHER_CONDITIONS_IMAGE_URL${weather.first().icon}@2x.png"
            )
        }
    }
}
