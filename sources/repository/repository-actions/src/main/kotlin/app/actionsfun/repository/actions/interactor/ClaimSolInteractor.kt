package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.tx.CreateTransactionInteractor
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ClaimSolInteractor {

    suspend fun claim(marketAddress: String): String
}

internal class ClaimSolInteractorImpl(
    private val createTransactionInteractor: CreateTransactionInteractor,
    private val walletRepository: WalletRepository,
) : ClaimSolInteractor {

    override suspend fun claim(marketAddress: String): String {
        return withContext(Dispatchers.IO) {
            val transaction = createTransactionInteractor.createClaimSolTransaction(
                marketAddress = marketAddress,
            )

            walletRepository.sendAndConfirmTransaction(transaction)
        }
    }
}
