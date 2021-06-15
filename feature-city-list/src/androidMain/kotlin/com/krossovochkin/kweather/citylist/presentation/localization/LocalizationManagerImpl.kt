package com.krossovochkin.kweather.citylist.presentation.localization

import android.content.Context
import androidx.annotation.StringRes
import com.krossovochkin.kweather.citylist.R
import com.krossovochkin.kweather.core.localization.LocalizationManager

actual class LocalizationManagerImpl(
    private val context: Context
) : LocalizationManager<LocalizedStringKey> {

    override fun getString(key: LocalizedStringKey): String {
        return context.getString(map(key))
    }

    @StringRes
    private fun map(key: LocalizedStringKey): Int {
        return when (key) {
            LocalizedStringKey.CityList_CityNameHint -> R.string.cityList_cityNameHint
        }
    }
}
