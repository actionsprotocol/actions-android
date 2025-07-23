package app.actionsfun.common.navigation

import app.actionsfun.common.navigation.core.Destination
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination : Destination {

    @Serializable
    data object Onboarding : AppDestination, Destination.Screen

    @Serializable
    data object Splash : AppDestination, Destination.Screen

    @Serializable
    data object Home : AppDestination, Destination.Screen

    @Serializable
    data object ConnectWallet : AppDestination, Destination.Sheet
}
