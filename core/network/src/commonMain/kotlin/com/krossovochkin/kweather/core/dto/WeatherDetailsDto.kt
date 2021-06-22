package com.krossovochkin.kweather.core.dto

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsDto(
    val id: Int,
    val name: String,
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
