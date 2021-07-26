package com.krossovochkin.kweather.domain

import kotlinx.datetime.LocalDateTime

data class WeatherDetails(
    val city: City,
    val todayWeatherData: TodayWeatherData,
    val tomorrowWeatherData: TomorrowWeatherData,
    val weekWeatherData: List<DailyWeatherData>,
) {

    data class TodayWeatherData(
        val currentWeatherData: CurrentWeatherData,
        val hourlyWeatherData: List<HourlyWeatherData>
    ) {

        data class CurrentWeatherData(
            val temperature: Int,
            val temperatureFeelsLike: Int,
            val pressure: Int,
            val humidity: Int,
            val windSpeed: Double,
            val windDegree: Int,
            val conditionImageUrl: String,
            val conditionDescription: String,
        )
    }

    data class TomorrowWeatherData(
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
    )

    data class DailyWeatherData(
        val localDateTime: LocalDateTime,
        val temperature: TemperatureData,
        val temperatureFeelsLike: TemperatureData,
        val pressure: Int,
        val humidity: Int,
        val windSpeed: Double,
        val windDegree: Int,
        val conditionImageUrl: String,
        val conditionDescription: String,
    ) {

        data class TemperatureData(
            val temperatureDay: Int,
        )
    }
}
