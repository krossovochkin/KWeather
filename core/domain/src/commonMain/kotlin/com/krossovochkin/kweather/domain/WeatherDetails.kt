package com.krossovochkin.kweather.domain

data class WeatherDetails(
    val city: City,
    val currentWeatherData: WeatherData
) {

    data class WeatherData(
        val temperature: Int,
        val conditionImageUrl: String
    )
}
