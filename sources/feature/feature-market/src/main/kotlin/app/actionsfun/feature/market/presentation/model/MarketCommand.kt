package app.actionsfun.feature.market.presentation.model

internal sealed interface MarketCommand {

    data object ObserveNetwork : MarketCommand

    data class LoadMarket(val marketAddress: String) : MarketCommand

    data class ClaimSol(
        val marketAddress: String
    ) : MarketCommand

    data class DepositSol(
        val marketAddress: String,
        val amountSol: Float,
        val option: Boolean,
    ) : MarketCommand
    
    data class SendReply(
        val marketAddress: String,
        val text: String,
    ) : MarketCommand
    
    data object ConnectWallet : MarketCommand
}