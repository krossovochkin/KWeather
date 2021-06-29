package com.krossovochkin.kweather.weatherdetails.presentation

sealed class WeatherDetailsAction {

    object Load : WeatherDetailsAction()

    object OpenSelectCityScreen : WeatherDetailsAction()
}
