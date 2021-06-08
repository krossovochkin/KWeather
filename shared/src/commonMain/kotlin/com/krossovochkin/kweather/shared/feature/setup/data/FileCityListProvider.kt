package com.krossovochkin.kweather.shared.feature.setup.data

import com.krossovochkin.kweather.shared.common.storage.CityDto
import kotlinx.coroutines.flow.Flow

expect class FileCityListProvider {

    fun observe(): Flow<CityDto>
}