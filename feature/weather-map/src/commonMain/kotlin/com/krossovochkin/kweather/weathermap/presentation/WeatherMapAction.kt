package com.krossovochkin.kweather.weathermap.presentation

sealed class WeatherMapAction {

    object Load : WeatherMapAction()

    object Back : WeatherMapAction()
}
