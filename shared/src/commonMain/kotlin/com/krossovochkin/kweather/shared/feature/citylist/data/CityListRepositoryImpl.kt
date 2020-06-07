package com.krossovochkin.kweather.shared.feature.citylist.data

import com.krossovochkin.kweather.shared.common.storage.citylist.CityListDatasource
import com.krossovochkin.kweather.shared.common.utils.IO
import com.krossovochkin.kweather.shared.feature.citylist.domain.City
import com.krossovochkin.kweather.shared.feature.citylist.domain.CityListRepository
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