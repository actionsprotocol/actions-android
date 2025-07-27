package app.actions

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.actionsfun.common.navigation.AppDestination
import app.actionsfun.common.navigation.core.AppNavigation
import app.actionsfun.common.navigation.core.Destination
import app.actionsfun.common.navigation.core.destination
import app.actionsfun.common.ui.style.AppTheme
import app.actionsfun.feature.onboarding.ui.OnboardingScreen
import app.actionsfun.feature.splash.ui.SplashScreen
import org.koin.compose.koinInject

@Composable
internal fun AppContainer(
    activity: AppCompatActivity,
    startDestination: Destination.Screen = AppDestination.Splash,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.Colors.Background.Primary)
    ) {
        AppNavigation(
            activity = activity,
            startDestination = startDestination,
            destinations = { navigator ->
                destination<AppDestination.Splash> {
                    SplashScreen(
                        navigator = navigator,
                        storeProvider = koinInject(),
                    )
                }
                destination<AppDestination.Onboarding> {
                    OnboardingScreen(
                        navigator = navigator,
                        storeProvider = koinInject(),
                    )
                }
            },
            sheets = { sheet, navigator -> },
            dialogs = { dialog, navigator -> },
            overlayScreens = { overlay, navigator -> },
            toasts = { toast, navigator -> }
        )
    }
}
