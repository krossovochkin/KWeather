package com.krossovochkin.kweather.domain

@JvmInline
value class CityId(
    val id: Int
) {
    companion object {
        val currentLocation = CityId(-1)
    }
}
