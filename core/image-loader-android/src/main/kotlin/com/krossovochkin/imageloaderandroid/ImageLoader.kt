package com.krossovochkin.imageloaderandroid

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

object ImageLoader {

    @Composable
    fun rememberImagePainter(url: String): Painter? {
        return coil.compose.rememberImagePainter(url)
    }
}
