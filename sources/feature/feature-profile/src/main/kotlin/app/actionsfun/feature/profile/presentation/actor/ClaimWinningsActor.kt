package app.actionsfun.feature.profile.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.profile.presentation.model.ProfileCommand.ClaimSol
import app.actionsfun.feature.profile.presentation.model.ProfileEvent.SolClaimFailed
import app.actionsfun.feature.profile.presentation.model.ProfileEvent.SolClaimed
import app.actionsfun.repository.actions.interactor.ClaimSolInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.profile.presentation.model.ProfileCommand as Command
import app.actionsfun.feature.profile.presentation.model.ProfileEvent as Event

internal class ClaimWinningsActor(
    private val claimSolInteractor: ClaimSolInteractor,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<ClaimSol>()
            .mapLatest { command ->
                runCatching { claimSolInteractor.claim(command.marketAddress) }
                    .mapCatching { signature ->
                        SolClaimed(
                            marketAddress = command.marketAddress,
                            transactionSignature = signature
                        )
                    }.getOrElse {
                        SolClaimFailed(
                            marketAddress = command.marketAddress,
                            error = it
                        )
                    }
            }
    }
}
