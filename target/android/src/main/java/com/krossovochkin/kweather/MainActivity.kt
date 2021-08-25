package com.krossovochkin.kweather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.krossovochkin.kweather.feature.citylist.CityListScreen
import com.krossovochkin.kweather.feature.weatherdetails.WeatherDetailsScreen
import com.krossovochkin.kweather.navigation.RouterDestination
import com.krossovochkin.kweather.navigation.navigationModule
import org.kodein.di.bind
import org.kodein.di.compose.withDI
import org.kodein.di.singleton

class MainActivity : AppCompatActivity() {

    private val parentDi by lazy { (application as App).di }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MaterialTheme(
                colors = lightColors(
                    background = colorResource(id = R.color.ic_launcher_background)
                )
            ) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                }

                ProvideWindowInsets {
                    val navController = rememberNavController()

                    withDI(
                        {
                            extend(parentDi)
                            bind<NavController>() with singleton { navController }
                            import(navigationModule)
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = RouterDestination.WeatherDetails.route
                        ) {
                            composable(RouterDestination.CityList.route) {
                                CityListScreen()
                            }
                            composable(RouterDestination.WeatherDetails.route) {
                                WeatherDetailsScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}
