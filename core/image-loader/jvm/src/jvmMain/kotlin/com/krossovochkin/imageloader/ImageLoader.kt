package com.krossovochkin.imageloader

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skia.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

/**
 * Implementation based on https://github.com/joreilly/PeopleInSpace/blob/main/compose-desktop/src/main/kotlin/main.kt
 */
object ImageLoader {

    @Composable
    fun rememberImagePainter(url: String): Painter? {
        return fetchImage(url)?.let { ImagePainter(it) }
    }

    @Composable
    private fun fetchImage(url: String): ImageBitmap? {
        var image by remember(url) { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(url) {
            loadFullImage(url)?.let {
                image = Image.makeFromEncoded(toByteArray(it)).toComposeImageBitmap()
            }
        }

        return image
    }

    private fun toByteArray(bitmap: BufferedImage): ByteArray {
        val baos = ByteArrayOutputStream()
        ImageIO.write(bitmap, "png", baos)
        return baos.toByteArray()
    }

    private suspend fun loadFullImage(source: String): BufferedImage? =
        withContext(Dispatchers.IO) {
            runCatching {
                val url = URL(source)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 5000
                connection.connect()

                val input: InputStream = connection.inputStream
                val bitmap: BufferedImage? = ImageIO.read(input)
                bitmap
            }.getOrNull()
        }

    private class ImagePainter(
        private val image: ImageBitmap?
    ) : Painter() {
        override val intrinsicSize: Size
            get() = image?.let { Size(image.width.toFloat(), image.height.toFloat()) }
                ?: Size.Zero

        override fun DrawScope.onDraw() {
            image?.let(::drawImage)
        }
    }
}
