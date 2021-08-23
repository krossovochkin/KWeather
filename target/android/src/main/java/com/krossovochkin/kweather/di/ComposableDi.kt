package com.krossovochkin.kweather.di

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.kodein.di.DI
import org.kodein.di.compose.LocalDI

@SuppressLint("ComposableNaming")
@Composable
fun withParentDI(builder: DI.MainBuilder.() -> Unit, content: @Composable () -> Unit) {
    val parentDi = LocalDI.current
    val di = DI {
        extend(parentDi)
        builder()
    }
    CompositionLocalProvider(LocalDI provides di) { content() }
}
