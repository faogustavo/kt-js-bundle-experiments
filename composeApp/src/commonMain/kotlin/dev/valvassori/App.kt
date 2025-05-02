package dev.valvassori

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import dev.valvassori.navigation.NavGraph
import org.koin.compose.KoinContext

@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader
            .Builder(context)
            .logger(DebugLogger())
            .crossfade(true)
            .build()
    }

    KoinContext {
        MaterialTheme {
            NavGraph()
        }
    }
}
