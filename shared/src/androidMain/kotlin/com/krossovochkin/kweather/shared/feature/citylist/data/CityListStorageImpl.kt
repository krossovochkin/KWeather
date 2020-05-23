package com.krossovochkin.kweather.shared.feature.citylist.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val FILE_NAME_CITY_LIST = "city.list.json"

actual class CityListStorageImpl(
    private val context: Context
) : CityListStorage {

    override suspend fun getCityListData(): String {
        return withContext(Dispatchers.IO) {
            context.assets
                .open(FILE_NAME_CITY_LIST)
                .bufferedReader()
                .use { it.readText() }
        }
    }
}