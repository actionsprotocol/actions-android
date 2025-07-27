package app.actionsfun.repository.solana.internal.core

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.encodeToJsonElement
import app.actionsfun.repository.solana.internal.core.api.AccountInfo
import app.actionsfun.repository.solana.internal.core.api.Blockhash
import app.actionsfun.repository.solana.internal.core.api.Commitment
import app.actionsfun.repository.solana.internal.core.api.Commitment.Finalized
import app.actionsfun.repository.solana.internal.core.api.EpochInfo
import app.actionsfun.repository.solana.internal.core.api.Health
import app.actionsfun.repository.solana.internal.core.api.IsBlockhashValidResult
import app.actionsfun.repository.solana.internal.core.api.TokenAccountBalance
import app.actionsfun.repository.solana.internal.core.api.TransactionSimulation
import app.actionsfun.repository.solana.internal.core.api.TransactionSimulationError
import app.actionsfun.repository.solana.internal.core.api.TransactionSimulationSuccess
import app.actionsfun.repository.solana.internal.core.exception.RpcException
import app.actionsfun.repository.solana.internal.core.rpc.Balance
import app.actionsfun.repository.solana.internal.core.rpc.BlockhashResponse
import app.actionsfun.repository.solana.internal.core.rpc.EpochInfoResult
import app.actionsfun.repository.solana.internal.core.rpc.GetAccountInfoResponse
import app.actionsfun.repository.solana.internal.core.rpc.GetMultipleAccountsResponse
import app.actionsfun.repository.solana.internal.core.rpc.GetTokenAccountsResponse
import app.actionsfun.repository.solana.internal.core.rpc.GetTokenApplyResponse
import app.actionsfun.repository.solana.internal.core.rpc.Identity
import app.actionsfun.repository.solana.internal.core.rpc.RpcErrorResponse
import app.actionsfun.repository.solana.internal.core.rpc.RpcRequest
import app.actionsfun.repository.solana.internal.core.rpc.RpcResponse
import app.actionsfun.repository.solana.internal.core.rpc.SimulateTransactionResponse
import app.actionsfun.repository.solana.internal.core.rpc.TokenAccountInfo
import app.actionsfun.repository.solana.internal.core.rpc.TokenAmount
import app.actionsfun.repository.solana.internal.core.rpc.TokenBalanceResult
import java.io.BufferedReader
import java.io.InputStreamReader
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.URL
import java.util.Base64
import app.actionsfun.repository.solana.internal.core.api.SignatureStatus
import app.actionsfun.repository.solana.internal.core.rpc.GetSignatureStatusesResponse

class Connection @JvmOverloads constructor(
    private val rpcUrl: String,
    private val commitment: Commitment = Finalized,
) {
    @JvmOverloads
    constructor(
        rpcUrl: RpcUrl,
        commitment: Commitment = Finalized,
    ) : this(rpcUrl.value, commitment)

    private val jsonParser = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun getBalance(walletAddress: PublicKey): BigInteger {
        val balance: Balance = rpcCall("getBalance", listOf(walletAddress.toBase58()))
        return balance.value
    }

    @JvmOverloads
    fun getTokenAccountBalance(
        accountAddress: PublicKey,
        commitment: Commitment = this.commitment,
    ): TokenAccountBalance {
        val result: TokenBalanceResult = rpcCall(
            "getTokenAccountBalance",
            listOf(
                Json.encodeToJsonElement(accountAddress.toBase58()),
                Json.encodeToJsonElement(mapOf("commitment" to commitment.toString())),
            ),
        )
        val (amount, decimals, uiAmountString) = result.value
        return TokenAccountBalance(
            amount = BigInteger(amount),
            decimals = decimals,
            uiAmount = uiAmountString,
        )
    }

    @JvmOverloads
    fun getLatestBlockhash(commitment: Commitment = this.commitment): String =
        this.getLatestBlockhashExtended(commitment).blockhash

    @JvmOverloads
    fun getLatestBlockhashExtended(commitment: Commitment = this.commitment): Blockhash {
        val result: BlockhashResponse = rpcCall(
            "getLatestBlockhash",
            listOf(mapOf("commitment" to commitment.toString())),
        )
        return Blockhash(
            blockhash = result.value.blockhash,
            slot = result.context.slot,
            lastValidBlockHeight = result.value.lastValidBlockHeight,
        )
    }

    @JvmOverloads
    fun isBlockhashValid(blockhash: String, commitment: Commitment = this.commitment): Boolean {
        val result: IsBlockhashValidResult = rpcCall(
            "isBlockhashValid",
            listOf(
                Json.encodeToJsonElement(blockhash),
                Json.encodeToJsonElement(mapOf("commitment" to commitment.toString())),
            ),
        )
        return result.value
    }

    fun getHealth(): Health {
        val result: String = rpcCall("getHealth", listOf<String>())
        return if (result == "ok") Health.Ok else Health.Error
    }

    fun getEpochInfo(): EpochInfo {
        val result: EpochInfoResult = rpcCall("getEpochInfo", listOf<String>())
        return EpochInfo(
            absoluteSlot = result.absoluteSlot,
            blockHeight = result.blockHeight,
            epoch = result.epoch,
            slotIndex = result.slotIndex,
            slotsInEpoch = result.slotsInEpoch,
            transactionCount = result.transactionCount,
        )
    }

    fun getIdentity(): PublicKey {
        val (identity) = rpcCall<Identity, String>("getIdentity", listOf())
        return PublicKey(identity)
    }

    fun getTransactionCount(): Long = rpcCall<Long, String>("getTransactionCount", listOf())

    fun getAccountInfo(accountAddress: PublicKey): AccountInfo? {
        val (value) = rpcCall<GetAccountInfoResponse, JsonElement>(
            "getAccountInfo",
            listOf(
                Json.encodeToJsonElement(accountAddress.toBase58()),
                Json.encodeToJsonElement(mapOf("encoding" to "base64")),
            ),
        )
        return value?.let {
            val data = Base64.getDecoder().decode(value.data[0])
            AccountInfo(
                data,
                executable = value.executable,
                lamports = value.lamports,
                owner = PublicKey(value.owner),
                rentEpoch = value.rentEpoch,
                space = value.space ?: data.size,
            )
        }
    }

    fun getMultipleAccounts(accountAddresses: List<PublicKey>): List<AccountInfo?> {
        val encodedAddresses = accountAddresses.map { it.toBase58() }

        val (value) = rpcCall<GetMultipleAccountsResponse, JsonElement>(
            "getMultipleAccounts",
            listOf(
                Json.encodeToJsonElement(encodedAddresses),
                Json.encodeToJsonElement(mapOf("encoding" to "base64")),
            ),
        )

        return value.map { accountValue ->
            accountValue?.let {
                val data = Base64.getDecoder().decode(it.data[0])
                AccountInfo(
                    data,
                    executable = it.executable,
                    lamports = it.lamports,
                    owner = PublicKey(it.owner),
                    rentEpoch = it.rentEpoch,
                    space = it.space ?: data.size,
                )
            }
        }
    }

    /**
     * Gets all token accounts for a given owner public key
     *
     * @param ownerPublicKey The public key of the token account owner
     * @param commitment The commitment level (defaults to the connection's commitment)
     * @return List of token account information
     */
    @JvmOverloads
    fun getTokenAccountsByOwner(
        ownerPublicKey: PublicKey,
        commitment: Commitment = this.commitment
    ): List<TokenAccountInfo> {
        val tokenProgramId = "TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA"
        return rpcCall<GetTokenAccountsResponse, JsonElement>(
            "getTokenAccountsByOwner",
            listOf(
                Json.encodeToJsonElement(ownerPublicKey.toBase58()),
                Json.encodeToJsonElement(mapOf("programId" to tokenProgramId)),
                Json.encodeToJsonElement(
                    mapOf(
                        "encoding" to "jsonParsed",
                        "commitment" to commitment.toString()
                    )
                )
            )
        ).value
    }

    fun getMinimumBalanceForRentExemption(space: Int): Long {
        return rpcCall(
            "getMinimumBalanceForRentExemption",
            listOf(Json.encodeToJsonElement(space)),
        )
    }

    fun getTokenSupply(tokenPubkey: String): TokenAmount {
        return rpcCall<GetTokenApplyResponse, JsonElement>(
            "getTokenSupply",
            listOf(Json.encodeToJsonElement(tokenPubkey)),
        ).value
    }

    fun requestAirdrop(accountAddress: PublicKey, amount: Long): String {
        return rpcCall(
            "requestAirdrop",
            listOf(
                Json.encodeToJsonElement(accountAddress.toBase58()),
                Json.encodeToJsonElement(amount),
            ),
        )
    }

    fun sendTransaction(transactionBytes: ByteArray): String {
        val encodedTransaction = Base64.getEncoder().encodeToString(transactionBytes)
        return rpcCall(
            "sendTransaction",
            listOf(
                Json.encodeToJsonElement(encodedTransaction),
                Json.encodeToJsonElement(mapOf("encoding" to "base64")),
            ),
        )
    }

    fun sendTransaction(transaction: Transaction): String {
        return sendTransaction(transaction.serialize())
    }

    fun sendTransaction(transaction: VersionedTransaction): String {
        return sendTransaction(transaction.serialize())
    }

    fun simulateTransaction(transactionBytes: ByteArray): TransactionSimulation {
        val encodedTransaction = Base64.getEncoder().encodeToString(transactionBytes)
        val result: SimulateTransactionResponse = rpcCall(
            "simulateTransaction",
            listOf(
                Json.encodeToJsonElement(encodedTransaction),
                Json.encodeToJsonElement(mapOf("encoding" to "base64")),
            ),
        )
        val (err, logs) = result.value
        if (err != null) {
            return when (err) {
                is JsonPrimitive -> TransactionSimulationError(err.content)
                else -> TransactionSimulationError(err.toString())
            }
        } else if (logs != null) {
            return TransactionSimulationSuccess(logs)
        }
        throw IllegalArgumentException("Unable to parse simulation response")
    }

    fun simulateTransaction(transaction: Transaction): TransactionSimulation {
        return simulateTransaction(transaction.serialize())
    }

    fun simulateTransaction(transaction: VersionedTransaction): TransactionSimulation {
        return simulateTransaction(transaction.serialize())
    }

    fun getSignatureStatuses(signatures: List<String>): List<SignatureStatus?> {
        val result: GetSignatureStatusesResponse = rpcCall(
            "getSignatureStatuses",
            listOf(
                Json.encodeToJsonElement(signatures),
                Json.encodeToJsonElement(mapOf("searchTransactionHistory" to true))
            )
        )
        return result.value
    }

    private inline fun <reified T, reified I : Any> rpcCall(method: String, params: List<I>): T {
        val connection = URL(rpcUrl).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.doOutput = true
        connection.outputStream.use {
            val body = Json.encodeToString(
                RpcRequest(method, params),
            )
            it.write(body.toByteArray())
        }
        val responseBody = connection.inputStream.use {
            BufferedReader(InputStreamReader(it)).use { reader ->
                reader.readText()
            }
        }
        connection.disconnect()
        try {
            val (result) = jsonParser.decodeFromString<RpcResponse<T>>(responseBody)
            return result
        } catch (_: SerializationException) {
            val (error) = jsonParser.decodeFromString<RpcErrorResponse>(responseBody)
            throw RpcException(error.code, error.message, responseBody)
        }
    }
}
