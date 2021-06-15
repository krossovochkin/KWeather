package com.krossovochkin.kweather.weatherdetails.presentation

import com.krossovochkin.kweather.core.image.Image

sealed class WeatherDetailsState {

    object Loading : WeatherDetailsState()

    data class Data(
        val cityNameText: String,
        val temperatureText: String,
        val weatherConditionsImage: Image,
        val weatherConditionsImageContentDescription: String,
        val changeCityButtonText: String
    ) : WeatherDetailsState()

    data class UnknownError(
        val error: Exception
    ) : WeatherDetailsState()

    data class CityUnknownError(
        val cityMissingMessageText: String,
        val selectCityButtonText: String
    ) : WeatherDetailsState()
}
