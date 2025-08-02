package app.actionsfun.common.ui.exoplayer

import android.annotation.SuppressLint
import android.content.Context
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

object ExoPlayerVideoCache {
    @SuppressLint("UnsafeOptInUsageError")
    private var cache: SimpleCache? = null

    @SuppressLint("UnsafeOptInUsageError")
    fun getInstance(context: Context): SimpleCache {
        return cache ?: synchronized(this) {
            cache ?: run {
                val cacheSize = 5L * 1024 * 1024
                val cacheDir = File(context.cacheDir, "video_cache")

                SimpleCache(
                    cacheDir,
                    LeastRecentlyUsedCacheEvictor(cacheSize),
                    StandaloneDatabaseProvider(context)
                ).also { cache = it }
            }
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    fun release() {
        cache?.release()
        cache = null
    }
}