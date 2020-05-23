package com.krossovochkin.kweather.shared.feature.citylist.data

import com.krossovochkin.kweather.shared.common.utils.IO
import com.krossovochkin.kweather.shared.feature.citylist.domain.City
import com.krossovochkin.kweather.shared.feature.citylist.domain.CityListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val CITY_LIST_SIZE = 30

class CityListRepositoryImpl(
    private val cityListStorage: CityListStorage,
    private val cityListMapper: CityListMapper,
    private val citySerializer: CitySerializer = CitySerializer
) : CityListRepository {

    private val cache = mutableListOf<City>()

    override suspend fun getCityList(query: String): List<City> {
        val cityList = if (cache.isNotEmpty()) {
            cache
        } else {
            load()
                .also { cache += it }
        }

        return cityList
            .filter { it.name.contains(query, ignoreCase = true) }
            .take(CITY_LIST_SIZE)
    }

    private suspend fun load(): List<City> {
        return withContext(Dispatchers.IO) {
            citySerializer.listFromJson(cityListStorage.getCityListData())
                .map(cityListMapper::map)
        }
    }
}