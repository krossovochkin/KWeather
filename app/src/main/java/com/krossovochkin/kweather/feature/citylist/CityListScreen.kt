package com.krossovochkin.kweather.feature.citylist

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
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
    val cityListViewModel: CityListViewModel = remember {
        val di = DI {
            extend(parentDi)
            import(cityListModule)
        }
        val viewModel by di.instance<CityListViewModel>()
        viewModel
    }
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
    androidx.compose.runtime.onDispose(callback = { onDispose() })
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
        alignment = Alignment.Center
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
        TextField(
            modifier = Modifier.padding(bottom = Dp(16f)).fillMaxWidth(),
            value = state.queryText,
            label = { Text(state.cityNameHintText) },
            onValueChange = {
                onAction(CityListAction.ChangeCityNameQuery(it))
            }
        )
        LazyColumnFor(items = state.cityList) { city ->
            Text(
                modifier = Modifier
                    .padding(top = Dp(16f), bottom = Dp(16f))
                    .clickable(onClick = { onAction(CityListAction.SelectCity(city)) }),
                text = city.name,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
private fun ErrorState(
    state: CityListState.Error
) {
    Text(text = "${state.error}")
}
