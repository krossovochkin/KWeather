package com.krossovochkin.kweather.feature.citylist

import androidx.compose.Composable
import androidx.compose.collectAsState
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.FilledTextField
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.unit.Dp
import com.krossovochkin.kweather.shared.feature.citylist.cityListModule
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListAction
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListState
import com.krossovochkin.kweather.shared.feature.citylist.presentation.CityListViewModel
import org.kodein.di.DI
import org.kodein.di.instance

@Composable
fun CityListScreen(
    parentDi: DI
) {
    val cityListViewModel: CityListViewModel = state {
        val di = DI {
            extend(parentDi)
            import(cityListModule)
        }
        val viewModel by di.instance<CityListViewModel>()
        viewModel
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

@Composable
private fun CityListScreenImpl(
    cityListState: CityListState?,
    onAction: (CityListAction) -> Unit,
    onDispose: () -> Unit
) {
    androidx.compose.onDispose(callback = { onDispose() })
    Surface(color = MaterialTheme.colors.background) {
        when (cityListState) {
            is CityListState.Loading -> LoadingState()
            is CityListState.Data -> DataState(
                state = cityListState,
                onAction = { onAction(it) }
            )
            is CityListState.Error -> ErrorState(state = cityListState)
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
private fun DataState(
    state: CityListState.Data,
    onAction: (CityListAction) -> Unit
) {
    Column(
        modifier = Modifier.padding(Dp(16f))
    ) {
        FilledTextField(
            modifier = Modifier.padding(bottom = Dp(16f)).fillMaxWidth(),
            value = state.queryText,
            label = { Text(state.cityNameHintText) },
            onValueChange = {
                onAction(CityListAction.ChangeCityNameQuery(it))
            }
        )
        AdapterList(data = state.cityList) { city ->
            Clickable(onClick = { onAction(CityListAction.SelectCity(city)) }) {
                Text(
                    modifier = Modifier.padding(top = Dp(16f), bottom = Dp(16f)),
                    text = city.name,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
private fun ErrorState(
    state: CityListState.Error
) {
    Text(text = "${state.error}")
}
