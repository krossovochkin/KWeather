package com.krossovochkin.kweather.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsDto(
    @SerialName("current")
    val currentWeatherData: HourlyWeatherDataDto,
    @SerialName("hourly")
    val hourlyWeatherData: List<HourlyWeatherDataDto>,
    @SerialName("daily")
    val dailyWeatherData: List<DailyWeatherDataDto>,
) {

    @Serializable
    data class HourlyWeatherDataDto(
        @SerialName("dt")
        val timestamp: Long,
        @SerialName("temp")
        val temperature: Double,
        @SerialName("feels_like")
        val temperatureFeelsLike: Double,
        @SerialName("pressure")
        val pressure: Int,
        @SerialName("humidity")
        val humidity: Int,
        @SerialName("wind_speed")
        val windSpeed: Double,
        @SerialName("wind_deg")
        val windDegree: Int,
        @SerialName("weather")
        val conditions: List<WeatherConditionDto>,
    )

    @Serializable
    data class DailyWeatherDataDto(
        @SerialName("dt")
        val timestamp: Long,
        @SerialName("temp")
        val temperature: TemperatureDataDto,
        @SerialName("feels_like")
        val temperatureFeelsLike: TemperatureDataDto,
        @SerialName("pressure")
        val pressure: Int,
        @SerialName("humidity")
        val humidity: Int,
        @SerialName("wind_speed")
        val windSpeed: Double,
        @SerialName("wind_deg")
        val windDegree: Int,
        @SerialName("weather")
        val conditions: List<WeatherConditionDto>,
    ) {

        @Serializable
        data class TemperatureDataDto(
            @SerialName("day")
            val temperatureDay: Double
        )
    }

    @Serializable
    data class WeatherConditionDto(
        @SerialName("icon")
        val icon: String,
        @SerialName("description")
        val description: String,
    )
}
