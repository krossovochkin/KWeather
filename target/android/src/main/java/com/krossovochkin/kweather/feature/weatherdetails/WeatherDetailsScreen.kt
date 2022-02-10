package com.krossovochkin.kweather.feature.weatherdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.insets.ui.TopAppBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.krossovochkin.imageloader.ImageLoader
import com.krossovochkin.kweather.R
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsAction
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsState
import com.krossovochkin.kweather.weatherdetails.presentation.WeatherDetailsViewModel
import com.krossovochkin.kweather.weatherdetails.weatherDetailsModule
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.instance

@Composable
fun WeatherDetailsScreen(parentDi: DI) {
    val di = remember {
        DI {
            extend(parentDi)
            import(weatherDetailsModule)
        }
    }

    val weatherDetailsViewModel: WeatherDetailsViewModel by di.instance()

    val weatherDetailsState = weatherDetailsViewModel
        .observeState()
        .collectAsState(WeatherDetailsState.Loading)
        .value
    WeatherDetailsScreenImpl(
        weatherDetailsState,
        weatherDetailsViewModel::performAction,
        weatherDetailsViewModel::dispose
    )
}

@Composable
private fun WeatherDetailsScreenImpl(
    weatherDetailsState: WeatherDetailsState,
    onAction: (WeatherDetailsAction) -> Unit,
    onDispose: () -> Unit
) {
    DisposableEffect(null) { onDispose { onDispose() } }
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
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun UnknownErrorState(state: WeatherDetailsState.UnknownError) {
    Text(
        modifier = Modifier.systemBarsPadding(),
        text = "${state.error}"
    )
}

private enum class WeatherTab(
    val text: String
) {
    Today("Today"),
    Tomorrow("Tomorrow"),
    Week("Week")
}

@Composable
private fun DataState(
    state: WeatherDetailsState.Data,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = state.cityNameText,
                    style = MaterialTheme.typography.h6
                )
                IconButton(onClick = {
                    onAction(WeatherDetailsAction.OpenSelectCityScreen)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(R.string.edit)
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { onAction(WeatherDetailsAction.OpenWeatherMapScreen) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Map,
                        contentDescription = null
                    )
                }
            },
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.statusBars,
                applyStart = true,
                applyTop = true,
                applyBottom = true,
            )
        )

        TabController(
            tabs = WeatherTab.values().toList(),
            defaultTab = WeatherTab.Today,
            title = { it.text }
        ) { tab ->
            when (tab) {
                WeatherTab.Today -> TodayDataState(weatherData = state.todayWeatherData)
                WeatherTab.Tomorrow -> TomorrowDataState(weatherData = state.tomorrowWeatherData)
                WeatherTab.Week -> WeekDataState(weekWeatherData = state.weekWeatherData)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabController(
    tabs: List<WeatherTab>,
    defaultTab: WeatherTab,
    title: (WeatherTab) -> String,
    block: @Composable (WeatherTab) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = tabs.indexOf(defaultTab)
    )

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        tabs
            .forEachIndexed { index, value ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = title(value)
                    )
                }
            }
    }

    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        block(tabs[page])
    }
}

@Composable
private fun TodayDataState(
    weatherData: WeatherDetailsState.Data.OneDayWeatherData
) {
    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = weatherData.temperatureDayText,
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = weatherData.temperatureNightText,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            weatherData.temperatureCurrentText?.let { temperatureText ->
                Text(
                    text = temperatureText,
                    style = MaterialTheme.typography.h1
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(144.dp),
                painter = ImageLoader.rememberImagePainter(weatherData.weatherConditionImageUrl),
                contentDescription = weatherData.weatherConditionImageContentDescription
            )
        }
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            weatherData.temperatureFeelsLikeText?.let { temperatureText ->
                Text(
                    text = temperatureText,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weatherData.weatherConditionDescription,
                style = MaterialTheme.typography.subtitle1
            )
        }
        Spacer(modifier = Modifier.weight(3f))
        LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
            items(weatherData.hourlyWeatherData) { HourlyWeatherItem(it) }
        }
    }
}

@Composable
private fun TomorrowDataState(
    weatherData: WeatherDetailsState.Data.OneDayWeatherData
) {
    Column(
        modifier = Modifier.navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Column {
                Row {
                    Text(
                        text = weatherData.temperatureDayText,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = weatherData.temperatureNightText,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                Spacer(Modifier.size(24.dp))
                Text(
                    text = weatherData.weatherConditionDescription,
                    style = MaterialTheme.typography.h4
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.size(144.dp),
                painter = ImageLoader.rememberImagePainter(weatherData.weatherConditionImageUrl),
                contentDescription = weatherData.weatherConditionImageContentDescription
            )
        }
        Spacer(modifier = Modifier.weight(3f))
        LazyRow(modifier = Modifier.padding(vertical = 16.dp)) {
            items(weatherData.hourlyWeatherData) {
                HourlyWeatherItem(it)
            }
        }
    }
}

@Composable
private fun WeekDataState(weekWeatherData: List<WeatherDetailsState.Data.DailyWeatherData>) {
    LazyColumn {
        itemsIndexed(weekWeatherData) { index, item ->
            DailyWeatherItem(item)
            Divider()
            if (index == weekWeatherData.size - 1) {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@Composable
private fun HourlyWeatherItem(
    weatherData: WeatherDetailsState.Data.HourlyWeatherData
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = weatherData.temperatureText,
            style = MaterialTheme.typography.caption
        )
        Image(
            modifier = Modifier.size(48.dp),
            painter = ImageLoader.rememberImagePainter(weatherData.weatherConditionsImageUrl),
            contentDescription = weatherData.weatherConditionsImageContentDescription
        )
        Text(
            text = weatherData.dateTimeText,
            style = MaterialTheme.typography.overline
        )
        Text(
            text = weatherData.precipitationVolumeText,
            style = MaterialTheme.typography.overline
        )
    }
}

@Composable
private fun DailyWeatherItem(
    weatherData: WeatherDetailsState.Data.DailyWeatherData
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = weatherData.dateTimeText,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = weatherData.weatherConditionsDescription,
                style = MaterialTheme.typography.body2
            )
        }
        Image(
            modifier = Modifier.size(72.dp),
            painter = ImageLoader.rememberImagePainter(weatherData.weatherConditionsImageUrl),
            contentDescription = weatherData.weatherConditionsImageContentDescription
        )
        Column {
            Text(
                text = weatherData.temperatureDayText,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = weatherData.temperatureNightText,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
private fun CityUnknownErrorState(
    state: WeatherDetailsState.CityUnknownError,
    onAction: (WeatherDetailsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
