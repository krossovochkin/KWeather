package com.krossovochkin.kweather.domain

import kotlinx.datetime.LocalDateTime

data class WeatherDetails(
    val city: City,
    val todayWeatherData: OneDayWeatherData,
    val tomorrowWeatherData: OneDayWeatherData,
    val weekWeatherData: List<DailyWeatherData>,
) {

    data class OneDayWeatherData(
        val weatherData: DailyWeatherData,
        val hourlyWeatherData: List<HourlyWeatherData>
    )

    data class HourlyWeatherData(
        val localDateTime: LocalDateTime,
        val temperature: Int,
        val temperatureFeelsLike: Int,
        val pressure: Int,
        val humidity: Int,
        val windSpeed: Double,
        val windDegree: Int,
        val conditionImageUrl: String,
        val conditionDescription: String,
        val precipitationVolume: Double,
    )

    data class DailyWeatherData(
        val localDateTime: LocalDateTime,
        val temperature: TemperatureData,
        val pressure: Int,
        val humidity: Int,
        val windSpeed: Double,
        val windDegree: Int,
        val conditionImageUrl: String,
        val conditionDescription: String,
    ) {

        data class TemperatureData(
            val temperatureDay: Int,
            val temperatureNight: Int,
            val temperatureCurrent: Int?,
            val temperatureFeelsLike: Int?
        )
    }
}
