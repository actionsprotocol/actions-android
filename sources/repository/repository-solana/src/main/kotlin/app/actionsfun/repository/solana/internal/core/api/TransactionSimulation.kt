package app.actionsfun.repository.solana.internal.core.api

sealed class TransactionSimulation

class TransactionSimulationError(val error: String) : TransactionSimulation()

class TransactionSimulationSuccess(val logs: List<String>) : TransactionSimulation()
