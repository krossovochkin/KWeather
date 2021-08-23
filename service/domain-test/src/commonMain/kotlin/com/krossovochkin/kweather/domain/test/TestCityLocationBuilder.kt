package com.krossovochkin.kweather.domain.test

import com.krossovochkin.kweather.domain.CityLocation

private const val DEFAULT_LATITUDE = 53.9
private const val DEFAULT_LONGITUDE = 27.5667

class TestCityLocationBuilder {

    private var latitude: Double = DEFAULT_LATITUDE
    private var longitude: Double = DEFAULT_LONGITUDE

    fun setLatitude(latitude: Double) = apply {
        this.latitude = latitude
    }

    fun setLongitude(longitude: Double) = apply {
        this.longitude = longitude
    }

    fun build(): CityLocation {
        return CityLocation(
            latitude = latitude,
            longitude = longitude
        )
    }
}
