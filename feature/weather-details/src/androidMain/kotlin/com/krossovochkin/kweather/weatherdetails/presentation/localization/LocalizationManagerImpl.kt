package com.krossovochkin.kweather.weatherdetails.presentation.localization

import android.content.Context
import androidx.annotation.StringRes
import com.krossovochkin.i18n.LocalizationManager
import com.krossovochkin.kweather.weatherdetails.R

internal actual class LocalizationManagerImpl(
    private val context: Context
) : LocalizationManager<LocalizedStringKey> {

    override fun getString(key: LocalizedStringKey): String {
        return context.getString(map(key))
    }

    override fun getString(key: LocalizedStringKey, vararg params: Any): String {
        return context.getString(map(key), *params)
    }

    @StringRes
    private fun map(key: LocalizedStringKey): Int {
        return when (key) {
            LocalizedStringKey.WeatherDetails_ChangeCity -> {
                R.string.weatherDetails_changeCity
            }
            LocalizedStringKey.WeatherDetails_CityMissingMessage -> {
                R.string.weatherDetails_cityMissingMessage
            }
            LocalizedStringKey.WeatherDetails_SelectCity -> {
                R.string.weatherDetails_selectCity
            }
            LocalizedStringKey.WeatherDetails_WeatherConditionsImageContentDescription -> {
                R.string.weatherDetails_weatherConditionsImageContentDescription
            }
            LocalizedStringKey.WeatherDetails_TemperatureDay -> {
                R.string.weatherDetails_temperatureDay
            }
            LocalizedStringKey.WeatherDetails_TemperatureNight -> {
                R.string.weatherDetails_temperatureNight
            }
            LocalizedStringKey.WeatherDetails_TemperatureFeelsLike -> {
                R.string.weatherDetails_temperatureFeelsLike
            }
        }
    }
}
