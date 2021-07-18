package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.dto.WeatherDetailsDto
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.CityId
import com.krossovochkin.kweather.domain.Location
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
                    name = dto.name,
                    location = Location(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                ),
                temperature = main.temperature.toInt(),
                weatherConditionsImageUrl = "$WEATHER_CONDITIONS_IMAGE_URL${weather.first().icon}@2x.png"
            )
        }
    }
}
