package com.krossovochkin.kweather

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.krossovochkin.kweather.features.citylist.CityListScreen
import com.krossovochkin.kweather.features.weatherdetails.WeatherDetailsScreen
import com.krossovochkin.kweather.navigation.RouterDestination
import com.krossovochkin.kweather.navigation.navigationModule
import com.krossovochkin.kweather.network.DI_TAG_API_KEY
import com.krossovochkin.kweather.network.networkModule
import com.krossovochkin.navigation.Router
import com.krossovochkin.permission.permissionModule
import com.krossovochkin.storage.storageModule
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

fun main(args: Array<String>) = application {
    val apiKey = args[0]
    Window(
        onCloseRequest = ::exitApplication
    ) {
        MaterialTheme {
            CompositionLocalProvider(
                LocalScrollbarStyle provides ScrollbarStyle(
                    minimalHeight = 16.dp,
                    thickness = 8.dp,
                    shape = MaterialTheme.shapes.small,
                    hoverDurationMillis = 300,
                    unhoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                    hoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.50f)
                )
            ) {
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
                    RouterDestination.WeatherMap -> Unit
                }
            }
        }
    }
}
