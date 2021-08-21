package com.krossovochkin.kweather

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.krossovochkin.kweather.core.DI_TAG_API_KEY
import com.krossovochkin.kweather.core.networkModule
import com.krossovochkin.kweather.features.citylist.CityListScreen
import com.krossovochkin.kweather.features.weatherdetails.WeatherDetailsScreen
import com.krossovochkin.navigation.Router
import com.krossovochkin.navigation.RouterDestination
import com.krossovochkin.navigation.navigationModule
import com.krossovochkin.permission.permissionModule
import com.krossovochkin.storage.storageModule
import org.jetbrains.compose.web.renderComposable
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun main() {
    renderComposable(rootElementId = "root") {
        val di = remember {
            DI {
                bind<String>(DI_TAG_API_KEY) with singleton { "" }
                import(storageModule)
                import(permissionModule)
                import(navigationModule)
                import(networkModule)
            }
        }

        val router: Router by di.instance()

        val screen = router.observeDestination()
            .collectAsState(RouterDestination.WeatherDetails)

        when (screen.value) {
            RouterDestination.CityList -> CityListScreen(di)
            RouterDestination.WeatherDetails -> WeatherDetailsScreen(di)
        }
    }
}
