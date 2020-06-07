package com.krossovochkin.kweather.shared.feature.weatherdetails.presentation

import com.krossovochkin.kweather.shared.common.image.Image
import com.krossovochkin.kweather.shared.feature.weatherdetails.domain.WeatherDetails

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
