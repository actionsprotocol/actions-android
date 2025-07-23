package app.actionsfun.common.navigation.core

import app.actionsfun.common.navigation.core.NavigationOption.ClearStack
import app.actionsfun.common.navigation.core.NavigationOption.SingleTop
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import app.actionsfun.common.navigation.core.NavigationOption.OverCurrentContent
import app.actionsfun.common.navigation.core.dialog.DialogState
import app.actionsfun.common.navigation.core.overlay.OverlayScreenState
import app.actionsfun.common.navigation.core.sheet.SheetState
import app.actionsfun.common.navigation.core.toast.ToastState

class AppNavigator(
    override val activity: AppCompatActivity,
    private val navController: NavController,
    private val sheetState: SheetState,
    private val dialogState: DialogState,
    private val overlayState: OverlayScreenState,
    private val toastState: ToastState,
) : Navigator {

    override fun open(
        destination: Destination,
        options: NavigationBuilder.() -> Unit
    ) {
        val config = NavigationBuilder().apply(options).build()

        config.popUpToDestination?.let { popUpTo ->
            when (popUpTo) {
                is Destination.Toast -> Unit
                is Destination.Dialog -> dialogState.hide()
                is Destination.Sheet -> sheetState.hide()
                is Destination.Screen -> {
                    navController.popBackStack(popUpTo, inclusive = false)
                    overlayState.hide()
                    sheetState.hide()
                    dialogState.hide()
                }
            }
        }

        if (config.options.contains(ClearStack)) {
            navController.popBackStack(navController.graph.startDestinationId, inclusive = true)
            overlayState.hide()
            sheetState.hide()
            dialogState.hide()
        }

        when (destination) {
            is Destination.Toast -> toastState.show(destination)
            is Destination.Dialog -> dialogState.show(destination)
            is Destination.Sheet -> sheetState.show(destination)
            is Destination.Screen -> {
                if (config.options.contains(OverCurrentContent)) {
                    overlayState.show(destination)
                } else {
                    navigateToScreen(destination, config)
                }
            }
        }
    }

    private fun navigateToScreen(
        destination: Destination.Screen,
        config: NavigationConfig
    ) {
        navController.navigate(destination) {
            if (config.options.contains(SingleTop)) {
                launchSingleTop = true
            }
        }
    }

    override fun back() {
        val isOverlayVisible = runBlocking { overlayState.currentScreen.first() } != null
        val isDialogVisible = runBlocking { dialogState.currentDialog.first() } != null
        val isSheetVisible = runBlocking { sheetState.currentSheet.first() } != null
        val canNavigateUp = navController.previousBackStackEntry != null

        when {
            isOverlayVisible -> overlayState.hide()
            isDialogVisible -> dialogState.hide()
            isSheetVisible -> sheetState.hide()
            canNavigateUp -> navController.popBackStack()
            else -> activity.finish()
        }
    }
}
