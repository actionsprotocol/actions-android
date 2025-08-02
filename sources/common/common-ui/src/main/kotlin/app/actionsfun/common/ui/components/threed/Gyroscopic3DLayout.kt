package app.actionsfun.common.ui.components.threed

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun Gyroscopic3DLayout(
    color: Color,
    shadowColor: Color,
    modifier: Modifier = Modifier,
    shadowAlignment: Alignment = Alignment.BottomEnd,
    shadowDepth: Dp = 2.dp,
    shape: Shape = RoundedCornerShape(16.dp),
    sensitivity: Float = 10f,
    maxShadowOffset: Dp = 4.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val isPreview = LocalInspectionMode.current

    var tiltX by remember { mutableFloatStateOf(0f) }
    var tiltY by remember { mutableFloatStateOf(0f) }
    var initialX by remember { mutableStateOf<Float?>(null) }
    var initialY by remember { mutableStateOf<Float?>(null) }

    val (baseShadowOffsetX, baseShadowOffsetY) = when (shadowAlignment) {
        Alignment.TopStart -> -shadowDepth to -shadowDepth
        Alignment.TopCenter -> 0.dp to -shadowDepth
        Alignment.TopEnd -> shadowDepth to -shadowDepth
        Alignment.CenterStart -> -shadowDepth to 0.dp
        Alignment.Center -> 0.dp to 0.dp
        Alignment.CenterEnd -> shadowDepth to 0.dp
        Alignment.BottomStart -> -shadowDepth to shadowDepth
        Alignment.BottomCenter -> 0.dp to shadowDepth
        Alignment.BottomEnd -> shadowDepth to shadowDepth
        else -> 0.dp to shadowDepth
    }

    val gyroOffsetX = (tiltX * sensitivity).coerceIn(-maxShadowOffset.value, maxShadowOffset.value).dp
    val gyroOffsetY = (tiltY * sensitivity).coerceIn(-maxShadowOffset.value, maxShadowOffset.value).dp

    val animatedTiltX by animateDpAsState(
        targetValue = gyroOffsetX,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 300f),
        label = "tiltX"
    )

    val animatedTiltY by animateDpAsState(
        targetValue = gyroOffsetY,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 300f),
        label = "tiltY"
    )

    if (!isPreview) {
        DisposableEffect(lifecycleOwner) {
            val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

            val sensorListener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    val x = event.values[0] / 10f
                    val y = event.values[1] / 10f

                    if (initialX == null || initialY == null) {
                        initialX = x
                        initialY = y
                    }

                    tiltX = (x - (initialX ?: 0f)).coerceIn(-1f, 1f)
                    tiltY = -(y - (initialY ?: 0f)).coerceIn(-1f, 1f)
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
            }

            val lifecycleObserver = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {
                        accelerometer?.let {
                            sensorManager.registerListener(
                                sensorListener,
                                it,
                                SensorManager.SENSOR_DELAY_GAME
                            )
                        }
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        sensorManager.unregisterListener(sensorListener)
                        initialX = null
                        initialY = null
                    }
                    else -> {}
                }
            }

            lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
                sensorManager.unregisterListener(sensorListener)
            }
        }
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(
                    x = baseShadowOffsetX + animatedTiltX,
                    y = baseShadowOffsetY + animatedTiltY
                )
                .clip(shape)
                .background(shadowColor)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(shadowDepth)
                .clip(shape)
                .background(color)
        ) {
            content()
        }
    }
}