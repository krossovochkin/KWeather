package com.krossovochkin.kweather.shared.common.storage.citylist

import com.krossovochkin.kweather.shared.common.storage.CityDto

interface CityListDatasource {

    suspend fun getCityList(query: String): List<CityDto>

    suspend fun getCityList(query: String, limit: Int): List<CityDto>

    suspend fun setCityList(cityList: List<CityDto>)
}
