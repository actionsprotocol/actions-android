package app.actionsfun.feature.market.presentation.model

import app.actionsfun.feature.market.domain.MarketWithClaimStatus
import java.math.BigDecimal

internal sealed interface MarketEvent {

    data object ObserveNetwork : MarketEvent

    data class LoadMarket(val marketAddress: String) : MarketEvent

    data class MarketLoaded(val market: MarketWithClaimStatus) : MarketEvent
    
    data class MarketLoadingError(val error: Throwable) : MarketEvent

    data class BalanceLoaded(val balance: BigDecimal) : MarketEvent

    data class ConnectionStateChanged(
        val connected: Boolean,
    ) : MarketEvent

    data class SolDeposited(
        val signature: String,
    ) : MarketEvent

    data class SolClaimed(
        val signature: String,
    ) : MarketEvent

    data class SolClaimFailed(
        val error: Throwable
    ) : MarketEvent

    data class SolDepositFailed(
        val error: Throwable,
    ) : MarketEvent
    
    data class ReplySent(
        val reply: String,
    ) : MarketEvent
    
    data class ReplySendFailed(
        val error: Throwable,
    ) : MarketEvent
    
    data class WalletStateChanged(
        val publicKey: String?,
    ) : MarketEvent
    
    data object WalletConnected : MarketEvent
    
    data class WalletConnectionFailed(
        val error: Throwable,
    ) : MarketEvent
}

internal sealed interface MarketUIEvent : MarketEvent {

    data class DepositQuickAmountClicked(val amount: Float) : MarketUIEvent

    data class DepositOptionChanged(val option: Boolean) : MarketUIEvent

    data class DepositAmountChanged(val value: Float) : MarketUIEvent

    data object DepositActionButtonClicked : MarketUIEvent
    
    data class SendReply(val text: String) : MarketUIEvent
    
    data object ConnectWallet : MarketUIEvent
}