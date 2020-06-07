package com.krossovochkin.kweather.shared.feature.weatherdetails.presentation

sealed class WeatherDetailsAction {

    object Load : WeatherDetailsAction()

    object OpenSelectCityScreen : WeatherDetailsAction()
}
