package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.kweather.core.image.Image
import com.krossovochkin.kweather.weatherdetails.domain.WeatherDetails

sealed class WeatherDetailsActionResult {

    data class Loaded(
        val weatherDetails: WeatherDetails,
        val weatherConditionsImage: Image
    ) : WeatherDetailsActionResult()

    object LoadErrorCityMissing : WeatherDetailsActionResult()

    data class LoadErrorUnknown(
        val e: Exception
    ) : WeatherDetailsActionResult()
}
