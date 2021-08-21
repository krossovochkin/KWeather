package com.krossovochkin.imageloader

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.krossovochkin.imageloaderandroid.ImageLoader

actual object ImageLoader {

    @Composable
    actual fun rememberImagePainter(url: String): Painter? {
       return ImageLoader.rememberImagePainter(url)
    }
}
