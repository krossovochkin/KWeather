package com.krossovochkin.kweather.shared.feature.setup.data

import com.krossovochkin.kweather.shared.common.storage.citylist.CityListDatasource
import com.krossovochkin.kweather.shared.feature.setup.domain.SetupRepository

class SetupRepositoryImpl(
    private val inputCityListDatasource: CityListDatasource,
    private val outputCityListDatasource: CityListDatasource
) : SetupRepository {

    override suspend fun setup() {
        if (outputCityListDatasource.getCityList("", 1).isNotEmpty()) {
            return
        }

        val cityList = inputCityListDatasource.getCityList("")
        outputCityListDatasource.setCityList(cityList)
    }
}
