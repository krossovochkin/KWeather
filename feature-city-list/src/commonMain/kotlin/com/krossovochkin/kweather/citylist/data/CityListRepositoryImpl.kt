package com.krossovochkin.kweather.citylist.data

import com.krossovochkin.kweather.citylist.domain.CityListRepository
import com.krossovochkin.kweather.core.domain.City
import com.krossovochkin.kweather.core.storage.citylist.CityListDatasource
import com.krossovochkin.kweather.core.storage.citylist.CityListMapper
import com.krossovochkin.kweather.core.utils.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CityListRepositoryImpl(
    private val cityListDatasource: CityListDatasource,
    private val cityListMapper: CityListMapper
) : CityListRepository {

    override suspend fun getCityList(query: String, limit: Int): List<City> {
        return withContext(Dispatchers.IO) {
            cityListDatasource.getCityList(query, limit)
                .map(cityListMapper::map)
        }
    }
}
