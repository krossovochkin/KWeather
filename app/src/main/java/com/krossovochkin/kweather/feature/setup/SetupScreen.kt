package com.krossovochkin.kweather.feature.setup

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupState
import com.krossovochkin.kweather.shared.feature.setup.presentation.SetupViewModel
import com.krossovochkin.kweather.shared.feature.setup.setupModule
import org.kodein.di.DI
import org.kodein.di.instance

@Composable
fun SetupScreen(
    parentDi: DI
) {
    val setupViewModel = remember {
        val di = DI {
            extend(parentDi)
            import(setupModule)
        }
        val viewModel by di.instance<SetupViewModel>()
        viewModel
    }
    val setupState = setupViewModel
        .observeState()
        .collectAsState(SetupState.Loading)
        .value
    SetupScreenImpl(
        setupState,
        setupViewModel::dispose
    )
}

@Composable
private fun SetupScreenImpl(
    setupState: SetupState?,
    onDispose: () -> Unit
) {
    androidx.compose.runtime.onDispose(callback = { onDispose() })
    Surface(color = MaterialTheme.colors.background) {
        when (setupState) {
            is SetupState.Loading -> LoadingState()
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
