package com.krossovochkin.kweather.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double
)
