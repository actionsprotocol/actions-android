package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.model.Participant
import app.actionsfun.repository.actions.internal.api.ActionsApi
import app.actionsfun.repository.actions.internal.api.model.SerializedParticipant
import app.actionsfun.repository.actions.internal.util.toParticipant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetMarketParticipantsInteractor {

    suspend fun get(marketAddress: String): List<Participant>
}

internal class GetMarketParticipantsInteractorImpl(
    private val api: ActionsApi,
) : GetMarketParticipantsInteractor {

    override suspend fun get(marketAddress: String): List<Participant> {
        return withContext(Dispatchers.IO) {
            runCatching { api.getParticipants(marketId = marketAddress) }
                .mapCatching { participants ->
                    participants.map(SerializedParticipant::toParticipant)
                }
                .getOrThrow()
        }
    }
}
