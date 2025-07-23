package app.actionsfun.common.arch.tea.util

import kotlinx.coroutines.flow.merge
import app.actionsfun.common.arch.tea.component.Actor

internal fun <Command : Any, Event : Any> combineActors(
    actors: Set<Actor<Command, Event>>,
): Actor<Command, Event> {
    return Actor { commands ->
        actors
            .map { actor -> actor.act(commands) }
            .merge()
    }
}