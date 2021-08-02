package com.krossovochkin.location

import com.krossovochkin.kweather.domain.Location

interface LocationProvider {

    suspend fun getLastLocation(): Location
}
