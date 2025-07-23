package app.actionsfun.feature.splash.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.splash.presentation.model.SplashEvent
import app.actionsfun.repository.onboarding.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.splash.presentation.model.SplashCommand as Command
import app.actionsfun.feature.splash.presentation.model.SplashEvent as Event

internal class LoadOnboardingStateActor(
    private val onboardingRepository: OnboardingRepository,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<Command.LoadOnboardingState>()
            .mapLatest { onboardingRepository.isOnboarded() }
            .mapLatest(SplashEvent::OnboardingStateLoaded)
    }
}