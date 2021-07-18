package com.krossovochkin.kweather.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsDto(
    @SerialName("current")
    val currentWeatherData: WeatherDataDto,
) {

    @Serializable
    data class WeatherDataDto(
        @SerialName("temp")
        val temperature: Double,
        @SerialName("weather")
        val conditions: List<WeatherConditionDto>
    ) {

        @Serializable
        data class WeatherConditionDto(
            @SerialName("icon")
            val icon: String,
        )
    }
}
