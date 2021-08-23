package com.krossovochkin.location.test

import com.krossovochkin.location.Location
import com.krossovochkin.location.LocationProvider

class TestLocationProvider : LocationProvider {

    var lastLocation = TestLocationBuilder().build()

    override suspend fun getLastLocation(): Location {
        return lastLocation
    }
}
