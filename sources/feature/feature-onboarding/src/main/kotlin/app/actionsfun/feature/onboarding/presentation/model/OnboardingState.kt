package app.actionsfun.feature.onboarding.presentation.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import app.actionsfun.feature.onboarding.R

internal data class OnboardingState(
    val screens: List<OnboardingScreenState>,
    val selectedScreen: Int,
) {
    companion object {

        val Default = OnboardingState(
            screens = listOf(
                OnboardingScreenState(
                    image = R.drawable.onboarding_0,
                    button = OnboardingButton.Default("Continue"),
                    color = Color(0xFFF6C635)
                ),
                OnboardingScreenState(
                    image = R.drawable.onboarding_1,
                    button = OnboardingButton.Default("Continue"),
                    color = Color(0xFF89D9E0)
                ),
                OnboardingScreenState(
                    image = R.drawable.onboarding_2,
                    button = OnboardingButton.Default("Let's go!"),
                    color = Color(0xFFF3D1DC)
                ),
            ),
            selectedScreen = 0,
        )
    }
}

internal data class OnboardingScreenState(
    @DrawableRes val image: Int,
    val button: OnboardingButton,
    val color: Color,
)

internal sealed interface OnboardingButton {
    val text: String

    data class Default(
        override val text: String
    ) : OnboardingButton
}
