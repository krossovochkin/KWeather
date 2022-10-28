package com.krossovochkin.location

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.krossovochkin.lifecycle.Lifecycle
import com.krossovochkin.permission.Permission
import com.krossovochkin.permission.PermissionManager
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

private const val INTERVAL_MILLIS = 1_000L

internal actual class LocationProviderImpl(
    private val permissionManager: PermissionManager,
    private val lifecycle: Lifecycle
) : LocationProvider {

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Location {
        val activity = requireNotNull(lifecycle.currentActivity)
        val locationClient = LocationServices.getFusedLocationProviderClient(activity)
        val hasPermission = permissionManager.requestPermission(Permission.FINE_LOCATION)

        check(hasPermission)

        return suspendCancellableCoroutine { continuation ->
            val callback = object : LocationCallback() {
                var isFinished = false
                override fun onLocationResult(locationResult: LocationResult) {
                    if (isFinished) {
                        return
                    }
                    val location = locationResult.lastLocation
                    if (location == null) {
                        return
                    }
                    isFinished = true
                    locationClient.removeLocationUpdates(this)
                    continuation.resume(
                        Location(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                }
            }

            locationClient.requestLocationUpdates(
                LocationRequest.Builder(INTERVAL_MILLIS)
                    .setWaitForAccurateLocation(true)
                    .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                    .build(),
                callback,
                Looper.getMainLooper()
            )

            continuation.invokeOnCancellation {
                locationClient.removeLocationUpdates(callback)
            }
        }
    }
}
