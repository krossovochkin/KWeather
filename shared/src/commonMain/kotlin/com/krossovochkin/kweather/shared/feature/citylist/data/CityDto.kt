package com.krossovochkin.kweather.shared.feature.citylist.data

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int,
    val name: String
)
