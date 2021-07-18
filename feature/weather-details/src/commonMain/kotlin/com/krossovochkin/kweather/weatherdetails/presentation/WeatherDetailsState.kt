package com.krossovochkin.kweather.weatherdetails.presentation

sealed class WeatherDetailsState {

    object Loading : WeatherDetailsState()

    data class Data(
        val cityNameText: String,
        val currentWeatherData: CurrentWeatherData,
        val changeCityButtonText: String
    ) : WeatherDetailsState() {

        data class CurrentWeatherData(
            val temperatureText: String,
            val weatherConditionsImageUrl: String,
            val weatherConditionsImageContentDescription: String,
        )
    }

    data class UnknownError(
        val error: Exception
    ) : WeatherDetailsState()

    data class CityUnknownError(
        val cityMissingMessageText: String,
        val selectCityButtonText: String
    ) : WeatherDetailsState()
}
