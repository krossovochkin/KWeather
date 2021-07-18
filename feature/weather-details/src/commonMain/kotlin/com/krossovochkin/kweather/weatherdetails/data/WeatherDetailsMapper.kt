package com.krossovochkin.kweather.weatherdetails.data

import com.krossovochkin.kweather.core.dto.WeatherDetailsDto
import com.krossovochkin.kweather.domain.City
import com.krossovochkin.kweather.domain.WeatherDetails

internal interface WeatherDetailsMapper {

    fun map(city: City, dto: WeatherDetailsDto): WeatherDetails
}

private const val WEATHER_CONDITIONS_IMAGE_URL = "https://openweathermap.org/img/wn/"

internal class WeatherDetailsMapperImpl : WeatherDetailsMapper {

    override fun map(city: City, dto: WeatherDetailsDto): WeatherDetails {
        return with(dto) {
            WeatherDetails(
                city = city,
                currentWeatherData = with(currentWeatherData) {
                    WeatherDetails.WeatherData(
                        temperature = temperature.toInt(),
                        temperatureFeelsLike = temperatureFeelsLike.toInt(),
                        pressure = pressure,
                        humidity = humidity,
                        windSpeed = windSpeed,
                        windDegree = windDegree,
                        conditionImageUrl = mapConditionImageUrl(
                            icon = conditions.first().icon
                        ),
                        conditionDescription = conditions.first().description
                    )
                }
            )
        }
    }

    private fun mapConditionImageUrl(icon: String): String {
        return "$WEATHER_CONDITIONS_IMAGE_URL$icon@2x.png"
    }
}
