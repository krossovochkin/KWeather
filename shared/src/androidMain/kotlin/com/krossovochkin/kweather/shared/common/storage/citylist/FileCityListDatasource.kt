package com.krossovochkin.kweather.shared.common.storage.citylist

import android.content.Context
import com.krossovochkin.kweather.shared.common.storage.CityDto
import com.krossovochkin.kweather.shared.common.storage.CitySerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val FILE_NAME_CITY_LIST = "city.list.json"

actual class FileCityListDatasource(
    private val context: Context,
    private val citySerializer: CitySerializer = CitySerializer
) : CityListDatasource {

    override suspend fun getCityList(query: String): List<CityDto> {
        return withContext(Dispatchers.IO) {
            getCityListInternal(query)
        }
    }

    override suspend fun getCityList(query: String, limit: Int): List<CityDto> {
        return withContext(Dispatchers.IO) {
            getCityListInternal(query)
                .take(limit)
        }
    }

    private fun getCityListInternal(query: String): List<CityDto> {
        return context.assets
            .open(FILE_NAME_CITY_LIST)
            .bufferedReader()
            .use { it.readText() }
            .let(citySerializer::listFromJson)
            .filter { query in it.name }
    }

    override suspend fun setCityList(cityList: List<CityDto>) {
        throw UnsupportedOperationException()
    }
}