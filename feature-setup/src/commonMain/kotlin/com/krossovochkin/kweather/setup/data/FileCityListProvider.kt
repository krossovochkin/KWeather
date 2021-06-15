package com.krossovochkin.kweather.setup.data

import com.krossovochkin.kweather.core.storage.CityDto
import kotlinx.coroutines.flow.Flow

expect class FileCityListProvider {

    fun observe(): Flow<CityDto>
}