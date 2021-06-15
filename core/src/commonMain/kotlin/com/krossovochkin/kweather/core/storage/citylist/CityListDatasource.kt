package com.krossovochkin.kweather.core.storage.citylist

import com.krossovochkin.kweather.core.storage.CityDto

interface CityListDatasource {

    suspend fun getCityList(query: String): List<CityDto>

    suspend fun getCityList(query: String, limit: Int): List<CityDto>
}
