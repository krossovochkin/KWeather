package com.krossovochkin.kweather.core.dto

import kotlinx.serialization.Serializable

@Serializable
data class CityListDto(
    val list: List<CityDto>
) {

    @Serializable
    data class CityDto(
        val id: Int,
        val name: String
    )

}
