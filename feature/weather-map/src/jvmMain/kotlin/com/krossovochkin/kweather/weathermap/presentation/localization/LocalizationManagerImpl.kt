package com.krossovochkin.kweather.weathermap.presentation.localization

import com.krossovochkin.i18n.LocalizationManager

internal actual class LocalizationManagerImpl : LocalizationManager<LocalizedStringKey> {

    override fun getString(key: LocalizedStringKey): String {
        return map(key)
    }

    override fun getString(key: LocalizedStringKey, vararg params: Any): String {
        return String.format(map(key), *params)
    }

    private fun map(key: LocalizedStringKey): String {
        return when (key) {
            LocalizedStringKey.WeatherMap_ToolbarTitle -> "Map"
        }
    }
}
