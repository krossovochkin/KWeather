package com.krossovochkin.location

import com.krossovochkin.kweather.domain.Location

internal actual class LocationProviderImpl : LocationProvider {

    override suspend fun getLastLocation(): Location {
        throw UnsupportedOperationException()
    }
}
