package com.krossovochkin.kweather.shared.common.storage

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int,
    val name: String
)
