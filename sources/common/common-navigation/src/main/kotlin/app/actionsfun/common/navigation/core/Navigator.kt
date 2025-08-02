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

    companion object {

        val None = object : Navigator {
            override val activity: AppCompatActivity
                get() = TODO("Not yet implemented")

            override fun open(destination: Destination, options: NavigationBuilder.() -> Unit) {
                TODO("Not yet implemented")
            }

            override fun back() {
                TODO("Not yet implemented")
            }
        }
    }
}
