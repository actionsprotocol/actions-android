package app.actionsfun.common.navigation.core

import app.actionsfun.common.navigation.core.NavAnimation.Fade
import app.actionsfun.common.navigation.core.NavAnimation.None
import app.actionsfun.common.navigation.core.NavAnimation.SlideRight
import app.actionsfun.common.navigation.core.NavAnimation.SlideUp
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

inline fun <reified T : Destination.Screen> NavGraphBuilder.destination(
    animate: NavAnimation = None,
    noinline content: @Composable (T?) -> Unit
) {
    composable<T>(
        enterTransition = {
            when (animate) {
                SlideUp -> slideInVertically { height -> height }
                SlideRight -> slideInHorizontally { width -> width }
                Fade -> fadeIn()
                None -> null
            }
        },
        exitTransition = {
            when (animate) {
                SlideUp -> slideOutVertically { height -> -height } // Slide up and out of view
                SlideRight -> slideOutHorizontally { width -> -width } // Slide left and out of view
                Fade -> fadeOut()
                None -> null
            }
        },
        popEnterTransition = {
            when (animate) {
                SlideUp -> slideInVertically { height -> -height } // Slide down from top when returning
                SlideRight -> slideInHorizontally { width -> -width } // Slide in from left when returning
                Fade -> fadeIn()
                None -> null
            }
        },
        popExitTransition = {
            when (animate) {
                SlideUp -> slideOutVertically { height -> height } // Slide down and out when going back
                SlideRight -> slideOutHorizontally { width -> width } // Slide right and out when going back
                Fade -> fadeOut()
                None -> null
            }
        }
    ) { backstack ->
        content(backstack.toRoute<T>())
    }
}

enum class NavAnimation {
    None,
    SlideUp,
    SlideRight,
    Fade
}