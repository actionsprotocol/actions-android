package app.actionsfun.feature.onboarding.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.repository.actions.interactor.GetMarketsInteractor
import app.actionsfun.repository.onboarding.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

import app.actionsfun.feature.onboarding.presentation.model.OnboardingCommand as Command
import app.actionsfun.feature.onboarding.presentation.model.OnboardingEvent as Event

internal class LoadMarketsActor(
    private val getMarketsInteractor: GetMarketsInteractor,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<Command.GetMarkets>()
            .mapLatest {
                getMarketsInteractor.get()
            }
            .mapLatest { Event.MarketsLoaded(it) }
    }
}