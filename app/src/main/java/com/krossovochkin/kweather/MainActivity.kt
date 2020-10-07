package com.krossovochkin.kweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import com.krossovochkin.kweather.feature.citylist.CityListScreen
import com.krossovochkin.kweather.feature.setup.SetupScreen
import com.krossovochkin.kweather.feature.weatherdetails.WeatherDetailsScreen
import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.common.router.RouterDestination
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

private const val BG_COLOR = 0xFFADD8E6

class MainActivity : AppCompatActivity(), DIAware {

    private val parentDi by lazy { (application as App).di }
    override val di: DI = DI.lazy {
        extend(parentDi)
    }
    private val router: Router by instance<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val di = (application as App).di

        setContent {
            MaterialTheme(
                colors = lightColors(
                    background = Color(BG_COLOR)
                )
            ) {
                val screen = router
                    .observeCurrentDestination
                    .collectAsState(initial = router.currentDestination)
                    .value

                when (screen) {
                    RouterDestination.Setup -> SetupScreen(parentDi = di)
                    RouterDestination.WeatherDetails -> WeatherDetailsScreen(parentDi = di)
                    RouterDestination.CityList -> CityListScreen(parentDi = di)
                }
            }
        }
    }
}
