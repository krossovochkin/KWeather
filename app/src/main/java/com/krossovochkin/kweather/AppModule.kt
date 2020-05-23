package com.krossovochkin.kweather

import android.content.Context
import com.krossovochkin.kweather.shared.common.image.ImageLoader
import com.krossovochkin.kweather.shared.common.localization.LocalizationManager
import com.krossovochkin.kweather.shared.common.localization.LocalizationManagerImpl
import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.common.router.RouterImpl
import com.krossovochkin.kweather.shared.common.storage.StorageModule

class AppModule(
    applicationContext: Context
) {

    val router: Router = RouterImpl()

    val storageModule: StorageModule = StorageModule(applicationContext)

    val imageLoader: ImageLoader = ImageLoader

    val localizationManager: LocalizationManager = LocalizationManagerImpl(applicationContext)
}