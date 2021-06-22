package com.krossovochkin.kweather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.krossovochkin.kweather.core.router.RouterDestination
import com.krossovochkin.kweather.feature.citylist.CityListScreen
import com.krossovochkin.kweather.feature.weatherdetails.WeatherDetailsScreen
import org.kodein.di.DI

private const val BG_COLOR = 0xFFADD8E6

class MainActivity : AppCompatActivity() {

    private val parentDi by lazy { (application as App).di }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colors = lightColors(
                    background = Color(BG_COLOR)
                )
            ) {
                val navController = rememberNavController()
                val di = remember {
                    DI.lazy {
                        extend(parentDi)
                        import(routerModule(navController))
                    }
                }
                NavHost(
                    navController = navController,
                    startDestination = RouterDestination.WeatherDetails.route
                ) {
                    composable(RouterDestination.CityList.route) {
                        CityListScreen(parentDi = di)
                    }
                    composable(RouterDestination.WeatherDetails.route) {
                        WeatherDetailsScreen(parentDi = di)
                    }
                }
            }
        }
    }
}
