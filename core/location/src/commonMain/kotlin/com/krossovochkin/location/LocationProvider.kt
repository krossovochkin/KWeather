package com.krossovochkin.location

interface LocationProvider {

    suspend fun getLastLocation(): Location
}
