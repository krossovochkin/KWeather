package com.krossovochkin.kweather.weathermap.presentation

import com.krossovochkin.kweather.weathermap.domain.WeatherMapData

sealed class WeatherMapActionResult {

    data class Loaded(
        val weatherMapData: WeatherMapData
    ) : WeatherMapActionResult()
}
