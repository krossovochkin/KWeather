package com.krossovochkin.location

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.krossovochkin.kweather.domain.Location
import com.krossovochkin.lifecycle.Lifecycle
import com.krossovochkin.permission.Permission
import com.krossovochkin.permission.PermissionManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal actual class LocationProviderImpl(
    private val permissionManager: PermissionManager,
    private val lifecycle: Lifecycle
) : LocationProvider {

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Location {
        val activity = requireNotNull(lifecycle.currentActivity)
        val locationClient = LocationServices.getFusedLocationProviderClient(activity)
        val hasPermission = permissionManager.requestPermission(Permission.COARSE_LOCATION)

        check(hasPermission)

        return suspendCoroutine { continuation ->
            val callback = object : LocationCallback() {
                var isFinished = false
                override fun onLocationResult(locationResult: LocationResult) {
                    if (isFinished) {
                        return
                    }
                    isFinished = true
                    locationClient.removeLocationUpdates(this)
                    val location = locationResult.lastLocation
                    continuation.resume(
                        Location(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }
            }

            locationClient.requestLocationUpdates(
                LocationRequest.create(),
                callback,
                Looper.getMainLooper()
            )
        }
    }
}
