package app.actionsfun.common.navigation.core

import androidx.appcompat.app.AppCompatActivity

sealed interface NavigationOption {
    data object SingleTop : NavigationOption
    data object ClearStack : NavigationOption
    data object OverCurrentContent : NavigationOption
}

interface Navigator {

    val activity: AppCompatActivity

    fun open(destination: Destination, options: NavigationBuilder.() -> Unit = {})

    fun back()
}
