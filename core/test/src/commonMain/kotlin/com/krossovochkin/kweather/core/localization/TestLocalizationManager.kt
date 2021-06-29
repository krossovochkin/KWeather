package com.krossovochkin.kweather.core.localization

import com.krossovochkin.i18n.LocalizationManager

class TestLocalizationManager<KeyT> : LocalizationManager<KeyT> {

    private val translations = mutableMapOf<KeyT, String>()

    override fun getString(key: KeyT): String {
        return translations[key]!!
    }

    fun put(key: KeyT, value: String) {
        translations[key] = value
    }
}
