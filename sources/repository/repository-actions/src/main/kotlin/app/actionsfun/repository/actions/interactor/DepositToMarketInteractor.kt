package app.actionsfun.repository.actions.interactor

import app.actionsfun.repository.actions.interactor.tx.CreateTransactionInteractor
import app.actionsfun.repository.solana.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

interface DepositToMarketInteractor {

    suspend fun deposit(marketAddress: String, amount: BigDecimal, option: Boolean): String
}

internal class DepositToMarketInteractorImpl(
    private val createTransactionInteractor: CreateTransactionInteractor,
    private val walletRepository: WalletRepository,
) : DepositToMarketInteractor {

    override suspend fun deposit(marketAddress: String, amount: BigDecimal, option: Boolean): String {
        return withContext(Dispatchers.IO) {
            val transaction = createTransactionInteractor.createDepositToMarketTransaction(
                marketAddress = marketAddress,
                amountLamports = amount,
                option = option,
            )

            walletRepository.sendAndConfirmTransaction(transaction)
        }
    }
}
