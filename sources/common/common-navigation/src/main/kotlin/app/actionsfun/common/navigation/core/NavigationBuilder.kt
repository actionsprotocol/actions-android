package app.actionsfun.common.navigation.core

class NavigationBuilder {
    private val options = mutableListOf<NavigationOption>()
    private var popUpToDestination: Destination? = null

    operator fun NavigationOption.unaryPlus() {
        options.add(this)
    }

    infix fun popUpTo(destination: Destination) {
        popUpToDestination = destination
    }

    internal fun build(): NavigationConfig = NavigationConfig(
        options = options.toList(),
        popUpToDestination = popUpToDestination,
    )
}

internal data class NavigationConfig(
    val options: List<NavigationOption>,
    val popUpToDestination: Destination?,
)
