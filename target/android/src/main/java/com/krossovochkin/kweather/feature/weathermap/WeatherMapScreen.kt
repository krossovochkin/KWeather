package com.krossovochkin.kweather.feature.weathermap

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import com.krossovochkin.imageloader.ImageLoader
import com.krossovochkin.kweather.weathermap.presentation.WeatherMapState
import com.krossovochkin.kweather.weathermap.presentation.WeatherMapViewModel
import com.krossovochkin.kweather.weathermap.weatherMapModule
import org.kodein.di.DI
import org.kodein.di.instance
import kotlin.math.abs

@Composable
fun WeatherMapScreen(parentDi: DI) {
    val di = remember {
        DI {
            extend(parentDi)
            import(weatherMapModule)
        }
    }
    val weatherMapViewModel: WeatherMapViewModel by di.instance()

    val weatherMapState = weatherMapViewModel
        .observeState()
        .collectAsState(WeatherMapState.Loading)
        .value

    WeatherMapScreenImpl(
        weatherMapState,
        weatherMapViewModel::dispose
    )
}

@Composable
private fun WeatherMapScreenImpl(
    weatherMapState: WeatherMapState?,
    onDispose: () -> Unit
) {
    DisposableEffect(null) { onDispose { onDispose() } }
    Surface(color = MaterialTheme.colors.background) {
        when (weatherMapState) {
            is WeatherMapState.Loading -> LoadingState()
            is WeatherMapState.Data -> DataState(
                state = weatherMapState
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DataState(
    state: WeatherMapState.Data
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = state.toolbarTitle,
                    style = MaterialTheme.typography.h6
                )
            },
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars,
                applyStart = true,
                applyTop = true,
                applyBottom = true,
            )
        )

        BoxWithConstraints {
            val tileSizeDp = maxWidth / 3

            val city = state.weatherMapData.city
            val tileBounds = state.weatherMapData.tileBounds

            MapTileView(state.weatherMapData.mapTileUrls, tileSizeDp)
            MapTileView(state.weatherMapData.precipitationTileUrls, tileSizeDp)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val offsetX = derivedStateOf {
                    val tileXDiff = abs(tileBounds.west - tileBounds.east)
                    val cityXDiff = abs(city.location.longitude - tileBounds.east)
                    tileSizeDp.toPx() + cityXDiff * tileSizeDp.toPx() / tileXDiff
                }
                val offsetY = derivedStateOf {
                    val tileYDiff = abs(tileBounds.south - tileBounds.north)
                    val cityYDiff = abs(city.location.latitude - tileBounds.north)
                    tileSizeDp.toPx() + cityYDiff * tileSizeDp.toPx() / tileYDiff
                }
                drawCircle(
                    Color.Black,
                    radius = 5f,
                    center = Offset(offsetX.value.toFloat(), offsetY.value.toFloat())
                )
            }
        }
    }
}

@Composable
private fun MapTileView(
    imageUrls: List<List<String>>,
    tileSizeDp: Dp,
) {
    Column {
        imageUrls.forEach { urls ->
            Row {
                urls.forEach { url ->
                    Image(
                        modifier = Modifier.size(tileSizeDp),
                        painter = ImageLoader.rememberImagePainter(url),
                        contentDescription = null
                    )
                }
            }
        }
    }
}
