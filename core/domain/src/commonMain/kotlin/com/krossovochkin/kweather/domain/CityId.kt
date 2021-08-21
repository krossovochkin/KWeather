package com.krossovochkin.kweather.domain

data class CityId(
    val id: Int
) {
    companion object {
        val currentLocation = CityId(-1)
    }
}
