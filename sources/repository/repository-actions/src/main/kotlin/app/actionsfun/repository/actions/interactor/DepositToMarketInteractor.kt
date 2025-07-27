package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.tx.CreateTransactionInteractor
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface DepositToMarketInteractor {

    suspend fun deposit(marketId: String, amount: Long, option: Boolean): String
}

internal class DepositToMarketInteractorImpl(
    private val createTransactionInteractor: CreateTransactionInteractor,
    private val walletRepository: WalletRepository,
) : DepositToMarketInteractor {

    override suspend fun deposit(marketId: String, amount: Long, option: Boolean): String {
        return withContext(Dispatchers.IO) {
            val transaction = createTransactionInteractor.createDepositToMarketTransaction(
                marketId = marketId,
                lamports = amount,
                option = option,
            )

            walletRepository.sendAndConfirmTransaction(transaction)
        }
    }
}
