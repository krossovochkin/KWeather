package com.krossovochkin.kweather.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityListDto(
    @SerialName("list")
    val list: List<CityDto>
) {

    @Serializable
    data class CityDto(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("coord")
        val location: LocationDto
    )
}
