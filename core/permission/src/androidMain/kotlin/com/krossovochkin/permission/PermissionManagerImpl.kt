package com.krossovochkin.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.krossovochkin.lifecycle.ActivityState
import com.krossovochkin.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

internal actual class PermissionManagerImpl(
    private val lifecycle: Lifecycle
) : PermissionManager {

    private val permissionReceivedFlow = MutableSharedFlow<Boolean>()
    private var requestLauncher: ActivityResultLauncher<String>? = null

    override suspend fun init() {
        lifecycle.observeActivity()
            .onEach { (state, activity) ->
                requestLauncher = when (state) {
                    ActivityState.CREATED -> {
                        (activity as AppCompatActivity)
                            .registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                                permissionReceivedFlow.tryEmit(it)
                            }
                    }
                    ActivityState.DESTROYED -> {
                        null
                    }
                }
            }
            .collect()
    }

    override suspend fun requestPermission(permission: Permission): Boolean {
        val activity = lifecycle.currentActivity ?: return false
        val requestLauncher = requestLauncher ?: return false

        val isGranted = ContextCompat.checkSelfPermission(
            activity,
            permission.toPermissionString()
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            return true
        }

        requestLauncher.launch(permission.toPermissionString())

        return permissionReceivedFlow.first()
    }

    private fun Permission.toPermissionString(): String {
        return when (this) {
            Permission.FINE_LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
        }
    }
}
