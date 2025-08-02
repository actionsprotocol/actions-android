package app.actionsfun.feature.profile.presentation.model

internal sealed interface ProfileEffect {

    data class ShowErrorToast(val message: String) : ProfileEffect

    data class ShowSuccessToast(val message: String) : ProfileEffect
}