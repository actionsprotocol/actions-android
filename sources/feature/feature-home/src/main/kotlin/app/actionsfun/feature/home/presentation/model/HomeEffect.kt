package app.actionsfun.feature.home.presentation.model

internal sealed interface HomeEffect {

    data object OpenProfile : HomeEffect

    data class ShowErrorToast(
        val text: String,
    ) : HomeEffect
}
