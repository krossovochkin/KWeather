package com.krossovochkin.kweather.feature.weatherdetails

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.state
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
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
            imageLoader = appModule.imageLoader,
            localizationManager = appModule.localizationManager,
            apiKey = appModule.apiKey
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
    Surface(color = MaterialTheme.colors.background) {
        when (weatherDetailsState) {
            is WeatherDetailsState.Loading -> LoadingState()
            is WeatherDetailsState.UnknownError -> UnknownErrorState(state = weatherDetailsState)
            is WeatherDetailsState.Data -> DataState(
                state = weatherDetailsState,
                onAction = { onAction(it) }
            )
            is WeatherDetailsState.CityUnknownError -> CityUnknownErrorState(
                state = weatherDetailsState,
                onAction = { onAction(it) }
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        gravity = ContentGravity.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun UnknownErrorState(state: WeatherDetailsState.UnknownError) {
    Text(text = "${state.error}")
}

@Composable
private fun DataState(
    state: WeatherDetailsState.Data,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(Dp(16f))
    ) {
        Text(
            modifier = Modifier.gravity(Alignment.CenterHorizontally),
            text = state.cityNameText,
            style = MaterialTheme.typography.h4
        )
        Row(
            modifier = Modifier.weight(1f, fill = true).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalGravity = Alignment.CenterVertically
        ) {
            state.weatherConditionsImage.drawable?.toBitmap()
                ?.asImageAsset()
                ?.let { Image(asset = it) }
            Text(
                modifier = Modifier.wrapContentWidth(align = Alignment.Start),
                text = state.temperatureText,
                style = MaterialTheme.typography.h5
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(WeatherDetailsAction.OpenSelectCityScreen) }
        ) {
            Text(text = state.changeCityButtonText)
        }
    }
}

@Composable
private fun CityUnknownErrorState(
    state: WeatherDetailsState.CityUnknownError,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(Dp(16f)).fillMaxSize(),
        horizontalGravity = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.weight(1f, fill = true),
            text = state.cityMissingMessageText
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onAction(WeatherDetailsAction.OpenSelectCityScreen) }
        ) {
            Text(text = state.selectCityButtonText)
        }
    }
}
