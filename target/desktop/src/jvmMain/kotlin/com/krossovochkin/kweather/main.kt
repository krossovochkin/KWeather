package com.krossovochkin.kweather

import androidx.compose.desktop.DesktopTheme
import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.window.application
import com.krossovochkin.kweather.network.DI_TAG_API_KEY
import com.krossovochkin.kweather.network.networkModule
import com.krossovochkin.kweather.features.citylist.CityListScreen
import com.krossovochkin.kweather.features.weatherdetails.WeatherDetailsScreen
import com.krossovochkin.navigation.Router
import com.krossovochkin.kweather.navigation.RouterDestination
import com.krossovochkin.kweather.navigation.navigationModule
import com.krossovochkin.permission.permissionModule
import com.krossovochkin.storage.storageModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun main(args: Array<String>) = application {
    val apiKey = args.get(0)
    Window {
        MaterialTheme {
            DesktopTheme {
                val di = remember {
                    DI {
                        bind<String>(DI_TAG_API_KEY) with singleton { apiKey }
                        import(storageModule)
                        import(permissionModule)
                        import(navigationModule)
                        import(networkModule)
                    }
                }

                val router: Router<RouterDestination> by di.instance()

                val screen = router.observeDestination()
                    .collectAsState(RouterDestination.WeatherDetails)

                when (screen.value) {
                    RouterDestination.CityList -> CityListScreen(di)
                    RouterDestination.WeatherDetails -> WeatherDetailsScreen(di)
                }
            }
        }
    }
}
