package com.krossovochkin.kweather.citylist.presentation.localization

import android.content.Context
import androidx.annotation.StringRes
import com.krossovochkin.i18n.LocalizationManager
import com.krossovochkin.kweather.citylist.R

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
            LocalizedStringKey.CityList_CityNameHint -> R.string.cityList_cityNameHint
        }
    }
}
