package com.krossovochkin.imageloader

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

expect object ImageLoader {

    @Composable
    fun rememberImagePainter(url: String): Painter?
}
