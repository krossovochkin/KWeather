package com.krossovochkin.i18n

interface LocalizationManager<KeyT> {

    fun getString(key: KeyT): String
}
