package app.actionsfun.feature.market.presentation.actor

import app.actionsfun.common.arch.tea.component.Actor
import app.actionsfun.feature.market.presentation.model.MarketCommand.SendReply
import app.actionsfun.feature.market.presentation.model.MarketEvent.ReplySent
import app.actionsfun.feature.market.presentation.model.MarketEvent.ReplySendFailed
import app.actionsfun.repository.actions.interactor.SendReplyInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import app.actionsfun.feature.market.presentation.model.MarketCommand as Command
import app.actionsfun.feature.market.presentation.model.MarketEvent as Event

internal class SendReplyActor(
    private val sendReplyInteractor: SendReplyInteractor,
) : Actor<Command, Event> {

    override fun act(commands: Flow<Command>): Flow<Event> {
        return commands.filterIsInstance<SendReply>()
            .mapLatest { command ->
                runCatching {
                    sendReplyInteractor.send(
                        marketId = command.marketAddress,
                        reply = command.text,
                    )
                    ReplySent(reply = command.text)
                }
                .getOrElse { error ->
                    ReplySendFailed(error = error)
                }
            }
    }
} 