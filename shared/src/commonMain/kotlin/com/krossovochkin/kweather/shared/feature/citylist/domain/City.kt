package com.krossovochkin.kweather.shared.feature.citylist.domain

import com.krossovochkin.kweather.shared.common.domain.CityId

data class City(
    val id: CityId,
    val name: String
)
