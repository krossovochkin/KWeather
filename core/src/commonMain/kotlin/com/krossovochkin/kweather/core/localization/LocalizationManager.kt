package com.krossovochkin.kweather.core.localization

interface LocalizationManager<KeyT> {

    fun getString(key: KeyT): String
}
