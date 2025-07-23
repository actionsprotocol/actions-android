package app.actionsfun.repository.solana.internal.core

enum class RpcUrl(val value: String) {
    Devnet("https://api.devnet.solana.com"),
    Mainnet("https://api.mainnet-beta.solana.com"),
    Testnet("https://api.testnet.solana.com"),
}
