package com.krossovochkin.kweather.core.storage

import kotlinx.serialization.Serializable

@Serializable
data class CityDto(
    val id: Int,
    val name: String
)
