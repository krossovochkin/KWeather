package com.krossovochkin.kweather.feature.weatherdetails

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.state
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.unit.Dp
import com.krossovochkin.kweather.AppModule
import com.krossovochkin.kweather.shared.feature.weatherdetails.WeatherDetailsModule
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsAction
import com.krossovochkin.kweather.shared.feature.weatherdetails.presentation.WeatherDetailsState

@Composable
fun WeatherDetailsScreen(
    appModule: AppModule
) {
    val weatherDetailsViewModel = state {
        WeatherDetailsModule(
            router = appModule.router,
            storageModule = appModule.storageModule,
            imageLoader = appModule.imageLoader
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

@Composable
private fun WeatherDetailsScreenImpl(
    weatherDetailsState: WeatherDetailsState?,
    onAction: (WeatherDetailsAction) -> Unit,
    onDispose: () -> Unit
) {
    androidx.compose.onDispose(callback = { onDispose() })
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
