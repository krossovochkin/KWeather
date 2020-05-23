package com.krossovochkin.kweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.onDispose
import androidx.compose.state
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.Dp
import com.krossovochkin.kweather.feature.citylist.CityListScreen
import com.krossovochkin.kweather.feature.weatherdetails.WeatherDetailsScreen
import com.krossovochkin.kweather.shared.common.image.ImageLoader
import com.krossovochkin.kweather.shared.common.router.Router
import com.krossovochkin.kweather.shared.common.router.RouterDestination
import com.krossovochkin.kweather.shared.common.router.RouterImpl
import com.krossovochkin.kweather.shared.common.storage.StorageModule
import com.krossovochkin.kweather.shared.feature.citylist.CityListModule
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListAction
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListState
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListViewModel
import com.krossovochkin.kweather.shared.feature.weatherdetails.WeatherDetailsModule
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsAction
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsState

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appModule = (application as App).appModule

        setContent {
            MaterialTheme {
                val screen = appModule.router
                    .observeCurrentDestination
                    .collectAsState(initial = appModule.router.currentDestination)
                    .value

                when (screen) {
                    RouterDestination.WeatherDetails -> WeatherDetailsScreen(appModule = appModule)
                    RouterDestination.CityList -> CityListScreen(appModule = appModule)
                }
            }
        }
    }
}



