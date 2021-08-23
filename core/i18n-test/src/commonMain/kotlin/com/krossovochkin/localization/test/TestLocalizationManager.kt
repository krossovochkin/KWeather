package com.krossovochkin.localization.test

import com.krossovochkin.i18n.LocalizationManager

class TestLocalizationManager<KeyT> : LocalizationManager<KeyT> {

    private val translations = mutableMapOf<Pair<KeyT, List<Any>>, String>()

    override fun getString(key: KeyT): String {
        return translations[key to emptyList()]!!
    }

    override fun getString(key: KeyT, vararg params: Any): String {
        return translations[key to params.toList()]!!
    }

    fun put(key: KeyT, value: String) {
        translations[key to emptyList()] = value
    }

    fun put(key: KeyT, params: List<Any>, value: String) {
        translations[key to params] = value
    }
}
