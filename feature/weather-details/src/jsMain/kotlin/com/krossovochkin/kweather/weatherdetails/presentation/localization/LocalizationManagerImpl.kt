package com.krossovochkin.kweather.weatherdetails.presentation.localization

import com.krossovochkin.i18n.LocalizationManager

internal actual class LocalizationManagerImpl : LocalizationManager<LocalizedStringKey> {

    override fun getString(key: LocalizedStringKey): String {
        return map(key)
    }

    override fun getString(key: LocalizedStringKey, vararg params: Any): String {
        var string = map(key)
        params.forEach { key ->
            string = string.replaceFirst("%1\$s", "$key")
        }
        return string
    }

    private fun map(key: LocalizedStringKey): String {
        return when (key) {
            LocalizedStringKey.WeatherDetails_ChangeCityText -> "Change city"
            LocalizedStringKey.WeatherDetails_CityMissingMessage -> "City is missing, add it first"
            LocalizedStringKey.WeatherDetails_SelectCity -> "Select city"
            LocalizedStringKey.WeatherDetails_WeatherConditionsImageContentDescription -> "Weather conditions image"
            LocalizedStringKey.WeatherDetails_TemperatureDay -> "Day: %s"
            LocalizedStringKey.WeatherDetails_TemperatureNight -> "Night: %s"
            LocalizedStringKey.WeatherDetails_TemperatureFeelsLike -> "Feels like: %s"
        }
    }
}
