package com.krossovochkin.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

internal class LifecycleImpl(
    private val application: Application
) : Lifecycle {

    private val activityRelay = MutableSharedFlow<Pair<ActivityState, Activity>>(replay = 1)

    override var currentActivity: Activity? = null

    override fun init() {
        application.registerActivityLifecycleCallbacks(
            object : Application.ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    this@LifecycleImpl.currentActivity = activity
                    activityRelay.tryEmit(ActivityState.CREATED to activity)
                }

                override fun onActivityDestroyed(activity: Activity) {
                    this@LifecycleImpl.currentActivity = null
                    activityRelay.tryEmit(ActivityState.DESTROYED to activity)
                }

                override fun onActivityStarted(activity: Activity) = Unit

                override fun onActivityResumed(activity: Activity) = Unit

                override fun onActivityPaused(activity: Activity) = Unit

                override fun onActivityStopped(activity: Activity) = Unit

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) =
                    Unit
            })
    }

    override fun observeActivity(): Flow<Pair<ActivityState, Activity>> {
        return activityRelay
    }
}
