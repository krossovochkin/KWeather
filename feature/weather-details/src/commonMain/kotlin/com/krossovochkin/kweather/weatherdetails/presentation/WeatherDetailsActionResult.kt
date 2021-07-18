package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.kweather.domain.WeatherDetails

sealed class WeatherDetailsActionResult {

    data class Loaded(
        val weatherDetails: WeatherDetails
    ) : WeatherDetailsActionResult()

    object LoadErrorCityMissing : WeatherDetailsActionResult()

    data class LoadErrorUnknown(
        val e: Exception
    ) : WeatherDetailsActionResult()
}
