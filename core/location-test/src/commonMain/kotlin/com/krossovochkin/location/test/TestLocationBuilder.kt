package com.krossovochkin.location.test

import com.krossovochkin.location.Location

private const val DEFAULT_LATITUDE = 53.9
private const val DEFAULT_LONGITUDE = 27.5667

class TestLocationBuilder {

    private var latitude: Double = DEFAULT_LATITUDE
    private var longitude: Double = DEFAULT_LONGITUDE

    fun setLatitude(latitude: Double) = apply {
        this.latitude = latitude
    }

    fun setLongitude(longitude: Double) = apply {
        this.longitude = longitude
    }

    fun build(): Location {
        return Location(
            latitude = latitude,
            longitude = longitude
        )
    }
}
