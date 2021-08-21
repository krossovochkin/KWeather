package com.krossovochkin.kweather.citylist.presentation.localization

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
            LocalizedStringKey.CityList_CityNameHint -> "City Name"
            LocalizedStringKey.CityList_UseCurrentLocation -> "Current Location"
        }
    }
}
