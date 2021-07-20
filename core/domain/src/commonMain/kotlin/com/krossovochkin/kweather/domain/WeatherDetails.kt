package com.krossovochkin.kweather.domain

data class WeatherDetails(
    val city: City,
    val currentWeatherData: WeatherData,
    val hourlyWeatherData: List<WeatherData>,
    val dailyWeatherData: List<WeatherData>,
) {

    data class WeatherData(
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
