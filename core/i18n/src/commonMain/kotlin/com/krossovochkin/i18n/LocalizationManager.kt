package com.krossovochkin.i18n

interface LocalizationManager<KeyT> {

    fun getString(key: KeyT): String

    fun getString(key: KeyT, vararg params: Any): String
}
