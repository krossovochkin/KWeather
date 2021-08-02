package com.krossovochkin.kweather.core.location

import com.krossovochkin.kweather.core.domain.TestLocationBuilder
import com.krossovochkin.kweather.domain.Location
import com.krossovochkin.location.LocationProvider

class TestLocationProvider : LocationProvider {

    var lastLocation = TestLocationBuilder().build()

    override suspend fun getLastLocation(): Location {
        return lastLocation
    }
}
