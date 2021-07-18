package com.krossovochkin.kweather.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("main")
    val main: MainDto,
    @SerialName("weather")
    val weather: List<WeatherDto>,
    @SerialName("coord")
    val location: LocationDto
) {

    @Serializable
    data class MainDto(
        @SerialName("temp")
        val temperature: Double
    )

    @Serializable
    data class WeatherDto(
        @SerialName("icon")
        val icon: String
    )
}
