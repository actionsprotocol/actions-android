package app.actionsfun.common.ui.exoplayer

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.compose.PlayerSurface
import androidx.media3.ui.compose.SURFACE_TYPE_SURFACE_VIEW
import androidx.media3.ui.compose.SurfaceType
import androidx.media3.ui.compose.modifiers.resizeWithContentScale
import androidx.media3.ui.compose.state.rememberPresentationState

@Composable
fun ExoPlayer(
    source: Uri,
    modifier: Modifier = Modifier,
    @SuppressLint("UnsafeOptInUsageError") surfaceType: @SurfaceType Int = SURFACE_TYPE_SURFACE_VIEW,
    useCache: Boolean = true,
    preview: @Composable () -> Unit = {},
    configure: ExoPlayer.() -> Unit = {},
) {
    ExoPlayer(
        sources = listOf(source),
        modifier = modifier,
        surfaceType = surfaceType,
        useCache = useCache,
        preview = preview,
        configure = configure,
    )
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun ExoPlayer(
    sources: List<Uri>,
    modifier: Modifier = Modifier,
    @SuppressLint("UnsafeOptInUsageError") surfaceType: @SurfaceType Int = SURFACE_TYPE_SURFACE_VIEW,
    useCache: Boolean = true,
    preview: @Composable () -> Unit = {},
    configure: ExoPlayer.() -> Unit = {},
) {
    val context = LocalContext.current
    var player by remember {
        mutableStateOf<Player?>(null)
    }

    LifecycleStartEffect(sources) {
        player = context.createPlayer(
            sources = sources,
            useCache = useCache,
            configure = configure
        )
        onStopOrDispose {
            player?.apply { release() }
            player = null
        }
    }

    player?.let { exoPlayer ->
        MediaPlayerScreen(
            player = exoPlayer,
            surfaceType = surfaceType,
        )
    }
}

@Composable
@SuppressLint("UnsafeOptInUsageError")
private fun MediaPlayerScreen(
    player: Player,
    modifier: Modifier = Modifier,
    @SuppressLint("UnsafeOptInUsageError") surfaceType: @SurfaceType Int = SURFACE_TYPE_SURFACE_VIEW,
) {
    val presentationState = rememberPresentationState(player)

    Box(modifier) {
        PlayerSurface(
            player = player,
            surfaceType = surfaceType,
            modifier = Modifier.resizeWithContentScale(
                ContentScale.Inside,
                presentationState.videoSizeDp
            ),
        )
    }
}

@SuppressLint("UnsafeOptInUsageError")
private fun Context.createPlayer(
    sources: List<Uri>,
    useCache: Boolean = true,
    configure: ExoPlayer.() -> Unit = {},
): Player {
    val builder = ExoPlayer.Builder(this)

    if (useCache) {
        val cache = ExoPlayerVideoCache.getInstance(this)

        val upstreamDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)
            .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
            .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)

        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(upstreamDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        val mediaSourceFactory = DefaultMediaSourceFactory(this)
            .setDataSourceFactory(cacheDataSourceFactory)

        builder.setMediaSourceFactory(mediaSourceFactory)
    }

    return builder.build().apply {
        configure()
        setMediaItems(sources.map(MediaItem::fromUri))
        prepare()
        play()
    }
}
