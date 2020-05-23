package com.krossovochkin.kweather.shared.common.storage

import android.content.Context
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListStorage
import com.krossovochkin.kweather.shared.feature.citylist.data.CityListStorageImpl

actual class StorageModule(
    private val context: Context
) {

    actual val storage: Storage
        get() = StorageImpl(context)

    actual val cityListStorage: CityListStorage
        get() = CityListStorageImpl(context)
}