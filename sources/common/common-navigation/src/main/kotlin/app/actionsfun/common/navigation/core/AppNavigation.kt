package app.actionsfun.common.navigation.core

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import app.actionsfun.common.navigation.core.dialog.DialogOverlay
import app.actionsfun.common.navigation.core.dialog.DialogState
import app.actionsfun.common.navigation.core.overlay.OverlayScreenState
import app.actionsfun.common.navigation.core.overlay.ScreenOverlay
import app.actionsfun.common.navigation.core.sheet.SheetOverlay
import app.actionsfun.common.navigation.core.sheet.SheetState
import app.actionsfun.common.navigation.core.toast.ToastOverlay
import app.actionsfun.common.navigation.core.toast.ToastState

@Composable
fun AppNavigation(
    activity: AppCompatActivity,
    startDestination: Destination.Screen,
    destinations: NavGraphBuilder.(Navigator) -> Unit,
    sheets: @Composable (Destination.Sheet, Navigator) -> Unit = { _, _ -> Unit },
    dialogs: @Composable (Destination.Dialog, Navigator) -> Unit = { _, _ -> Unit },
    overlayScreens: @Composable (Destination.Screen, Navigator) -> Unit = { _, _ -> },
    toasts: @Composable (Destination.Toast, Navigator) -> Unit = { _, _ -> },
) {
    val navController = rememberNavController()
    val sheetState = remember { SheetState() }
    val dialogState = remember { DialogState() }
    val overlayState = remember { OverlayScreenState() }
    val toastState = remember { ToastState() }
    val navigator = remember(navController, sheetState) {
        AppNavigator(
            activity = activity,
            navController = navController,
            sheetState = sheetState,
            dialogState = dialogState,
            overlayState = overlayState,
            toastState = toastState,
        )
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        destinations(navigator)
    }

    SheetOverlay(
        sheetState = sheetState,
        navigator = navigator,
        content = sheets
    )

    ScreenOverlay(
        overlayScreenState = overlayState,
        navigator = navigator,
        content = overlayScreens
    )

    DialogOverlay(
        dialogState = dialogState,
        navigator = navigator,
        content = dialogs,
    )

    ToastOverlay(
        toastState = toastState,
        navigator = navigator,
        content = toasts,
    )
}