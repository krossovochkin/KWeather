package com.krossovochkin.location

internal actual class LocationProviderImpl : LocationProvider {

    override suspend fun getLastLocation(): Location {
        throw UnsupportedOperationException()
    }
}
