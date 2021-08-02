package com.krossovochkin.lifecycle

import android.app.Activity
import kotlinx.coroutines.flow.Flow

interface Lifecycle {

    val currentActivity: Activity?

    fun init()

    fun observeActivity(): Flow<Pair<ActivityState, Activity>>
}

enum class ActivityState {
    CREATED,
    DESTROYED
}
