package com.krossovochkin.kweather.weatherdetails.presentation

sealed class WeatherDetailsState {

    object Loading : WeatherDetailsState()

    data class Data(
        val cityNameText: String,
        val todayWeatherData: OneDayWeatherData,
        val tomorrowWeatherData: OneDayWeatherData,
        val weekWeatherData: List<DailyWeatherData>,
    ) : WeatherDetailsState() {

        data class OneDayWeatherData(
            val temperatureDayText: String,
            val temperatureNightText: String,
            val temperatureCurrentText: String?,
            val temperatureFeelsLikeText: String?,
            val weatherConditionImageUrl: String,
            val weatherConditionImageContentDescription: String,
            val weatherConditionDescription: String,
            val hourlyWeatherData: List<HourlyWeatherData>
        )

        data class HourlyWeatherData(
            val dateTimeText: String,
            val temperatureText: String,
            val weatherConditionsImageUrl: String,
            val weatherConditionsImageContentDescription: String,
            val precipitationVolumeText: String,
        )

        data class DailyWeatherData(
            val dateTimeText: String,
            val temperatureDayText: String,
            val temperatureNightText: String,
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
