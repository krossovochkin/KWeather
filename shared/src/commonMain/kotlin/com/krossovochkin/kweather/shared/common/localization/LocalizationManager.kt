package com.krossovochkin.kweather.shared.common.localization

interface LocalizationManager {

    fun getString(key: LocalizedStringKey): String
}