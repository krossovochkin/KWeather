package com.krossovochkin.kweather.weathermap.presentation

import com.krossovochkin.kweather.weathermap.domain.WeatherMapData

sealed class WeatherMapState {

    object Loading : WeatherMapState()

    data class Data(
        val toolbarTitle: String,
        val weatherMapData: WeatherMapData,
    ) : WeatherMapState()
}
