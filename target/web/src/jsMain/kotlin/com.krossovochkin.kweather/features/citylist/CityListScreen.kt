@file:OptIn(ExperimentalComposeWebWidgetsApi::class)

package com.krossovochkin.kweather.features.citylist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.krossovochkin.kweather.citylist.cityListModule
import com.krossovochkin.kweather.citylist.presentation.CityListAction
import com.krossovochkin.kweather.citylist.presentation.CityListState
import com.krossovochkin.kweather.citylist.presentation.CityListViewModel
import com.krossovochkin.kweather.domain.City
import org.jetbrains.compose.common.foundation.layout.Box
import org.jetbrains.compose.common.foundation.layout.Column
import org.jetbrains.compose.common.foundation.layout.fillMaxHeight
import org.jetbrains.compose.common.foundation.layout.fillMaxWidth
import org.jetbrains.compose.common.ui.ExperimentalComposeWebWidgetsApi
import org.jetbrains.compose.common.ui.Modifier
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.TextInput
import org.kodein.di.DI
import org.kodein.di.instance

@Composable
fun CityListScreen(parentDi: DI) {
    val di = remember {
        DI {
            extend(parentDi)
            import(cityListModule)
        }
    }
    val cityListViewModel: CityListViewModel by di.instance()

    val cityListState = cityListViewModel
        .observeState()
        .collectAsState(CityListState.Loading)
        .value
    CityListScreenImpl(
        cityListState,
        cityListViewModel::performAction,
        cityListViewModel::dispose
    )
}

@Composable
private fun CityListScreenImpl(
    cityListState: CityListState?,
    onAction: (CityListAction) -> Unit,
    onDispose: () -> Unit
) {
    DisposableEffect(null) { onDispose { onDispose() } }
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(1f)
    ) {
        when (cityListState) {
            is CityListState.Loading -> CityListLoadingState()
            is CityListState.Data -> DataState(
                state = cityListState,
                onAction = { onAction(it) }
            )
            is CityListState.Error -> ErrorState(state = cityListState)
        }
    }
}

@Composable
private fun CityListLoadingState() {
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(1f)
    ) {
        Text("Loading...")
    }
}

@Composable
private fun DataState(
    state: CityListState.Data,
    onAction: (CityListAction) -> Unit
) {
    Column {
        TextInput(
            value = state.queryText,
            attrs = {
                onInput { onAction(CityListAction.ChangeCityNameQuery(it.value)) }
            }
        )
        state.cityList.forEach { city ->
            CityItem(city, onAction)
        }
    }
}

@Composable
private fun CityItem(city: City, onAction: (CityListAction) -> Unit) {
    Span(
        attrs = {
            onClick { onAction(CityListAction.SelectCity(city)) }
        }
    ) {
        Text(city.name)
    }
}

@Composable
private fun ErrorState(
    state: CityListState.Error
) {
    Text("${state.error}")
}
