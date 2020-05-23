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

        val router: Router = RouterImpl()
        val storageModule = StorageModule(this.applicationContext)

        setContent {
            MaterialTheme {
                val screen = router
                    .observeCurrentDestination
                    .collectAsState(initial = router.currentDestination)
                    .value

                when (screen) {
                    RouterDestination.WeatherDetails -> {
                        val weatherDetailsViewModel = state {
                            WeatherDetailsModule(
                                router = router,
                                storageModule = storageModule,
                                imageLoader = ImageLoader
                            ).viewModel
                        }.value
                        val weatherDetailsState = weatherDetailsViewModel
                            .observeState()
                            .collectAsState()
                            .value
                        WeatherDetailsScreenImpl(
                            weatherDetailsState,
                            weatherDetailsViewModel::performAction,
                            weatherDetailsViewModel::dispose
                        )
                    }
                    RouterDestination.CityList -> {
                        val cityListViewModel: CityListViewModel = state {
                            CityListModule(
                                router = router,
                                storageModule = storageModule
                            ).viewModel
                        }.value
                        val cityListState = cityListViewModel
                            .observeState()
                            .collectAsState()
                            .value
                        CityListScreenImpl(
                            cityListState,
                            cityListViewModel::performAction,
                            cityListViewModel::dispose
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CityListScreenImpl(
    cityListState: CityListState?,
    onAction: (CityListAction) -> Unit,
    onDispose: () -> Unit
) {
    onDispose(callback = { onDispose() })
    return when (cityListState) {
        is CityListState.Loading -> {
            Text(text = "Loading")
        }
        is CityListState.Data -> {
            Column(
                modifier = Modifier.padding(Dp(16f))
            ) {
                FilledTextField(
                    value = cityListState.queryText,
                    label = { Text("City name") },
                    onValueChange = {
                        onAction(CityListAction.ChangeCityNameQuery(it))
                    }
                )
                AdapterList(data = cityListState.cityList) { city ->
                    Clickable(onClick = { onAction(CityListAction.SelectCity(city)) }) {
                        Text(text = city.name)
                    }
                }
            }
        }
        is CityListState.Error -> {
            Text(text = "Error: ${cityListState.error}")
        }
        else -> {
        }
    }
}

@Composable
fun WeatherDetailsScreenImpl(
    weatherDetailsState: WeatherDetailsState?,
    onAction: (WeatherDetailsAction) -> Unit,
    onDispose: () -> Unit
) {
    onDispose(callback = { onDispose() })
    return when (weatherDetailsState) {
        is WeatherDetailsState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                gravity = ContentGravity.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is WeatherDetailsState.UnknownError -> {
            Text(text = "Error: ${weatherDetailsState.error}")
        }
        is WeatherDetailsState.Data -> {
            Column(Modifier.padding(Dp(16f))) {
                Text(text = weatherDetailsState.cityNameText)
                Text(text = weatherDetailsState.temperatureText)
                weatherDetailsState.weatherConditionsImage.drawable?.toBitmap()?.asImageAsset()
                    ?.let { Image(asset = it) }
                Button(onClick = { onAction(WeatherDetailsAction.OpenSelectCityScreen) }) {
                    Text(text = "Change city") // TODO: res
                }
            }

        }
        is WeatherDetailsState.CityUnknownError -> {
            Clickable(onClick = { onAction(WeatherDetailsAction.OpenSelectCityScreen) }) {
                Text(text = "City is missing, add it first")
            }
        }
        else -> {
        }
    }
}
