package com.krossovochkin.kweather.weatherdetails.data

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsDto(
    val main: MainDto,
    val weather: List<WeatherDto>
) {

    @Serializable
    data class MainDto(
        val temp: Double
    )

    @Serializable
    data class WeatherDto(
        val icon: String
    )
}
