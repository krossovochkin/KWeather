package com.krossovochkin.kweather.weatherdetails.presentation

sealed class WeatherDetailsState {

    object Loading : WeatherDetailsState()

    data class Data(
        val cityNameText: String,
        val todayWeatherData: OneDayWeatherData,
        val tomorrowWeatherData: OneDayWeatherData,
        val weekWeatherData: List<DailyWeatherData>,
        val changeCityButtonText: String
    ) : WeatherDetailsState() {

        data class OneDayWeatherData(
            val currentTemperatureText: String?,
            val weatherConditionsImageUrl: String,
            val weatherConditionsImageContentDescription: String,
            val hourlyWeatherData: List<HourlyWeatherData>
        )

        data class HourlyWeatherData(
            val dateTimeText: String,
            val temperatureText: String,
            val weatherConditionsImageUrl: String,
            val weatherConditionsImageContentDescription: String,
        )

        data class DailyWeatherData(
            val dateTimeText: String,
            val temperatureText: String,
            val weatherConditionsImageUrl: String,
            val weatherConditionsImageContentDescription: String,
            val weatherConditionsDescription: String
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
