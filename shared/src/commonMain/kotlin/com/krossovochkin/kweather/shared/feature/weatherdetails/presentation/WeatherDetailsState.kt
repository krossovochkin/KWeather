package com.krossovochkin.kweather.shared.feature.weatherdetails.presentation

import com.krossovochkin.kweather.shared.common.image.Image

sealed class WeatherDetailsState {

    object Loading : WeatherDetailsState()

    data class Data(
        val cityNameText: String,
        val temperatureText: String,
        val weatherConditionsImage: Image
    ) : WeatherDetailsState()

    data class UnknownError(
        val error: Exception
    ) : WeatherDetailsState()

    object CityUnknownError : WeatherDetailsState()
}

