package app.actionsfun.repository.actions.internal.api

import app.actionsfun.repository.actions.internal.api.model.AddChatMessageRequest
import app.actionsfun.repository.actions.internal.api.model.AddChatMessageResponse
import app.actionsfun.repository.actions.internal.api.model.ClaimData
import app.actionsfun.repository.actions.internal.api.model.CreatorFeesData
import app.actionsfun.repository.actions.internal.api.model.GenerateInstructionRequest
import app.actionsfun.repository.actions.internal.api.model.GenerateInstructionResponse
import app.actionsfun.repository.actions.internal.api.model.GetChatMessagesResponse
import app.actionsfun.repository.actions.internal.api.model.GetMarketVaultResponse
import app.actionsfun.repository.actions.internal.api.model.GetMarketsResponse
import app.actionsfun.repository.actions.internal.api.model.MarketResponseItem
import app.actionsfun.repository.actions.internal.api.model.SerializedParticipant
import retrofit2.http.*

internal interface ActionsApi {

    @GET("api/v2/markets")
    suspend fun getMarkets(): List<MarketResponseItem>

    @GET("api/v2/markets/{marketId}")
    suspend fun getMarket(
        @Path("marketId") marketId: String
    ): MarketResponseItem

    @GET("api/v2/markets/{marketId}/participants")
    suspend fun getParticipants(
        @Path("marketId") marketId: String
    ): List<SerializedParticipant>

    @GET("api/v2/markets/{marketId}/claim/{userAddress}")
    suspend fun getMarketClaim(
        @Path("marketId") marketId: String,
        @Path("userAddress") userAddress: String
    ): ClaimData

    @GET("api/v2/markets/{marketId}/vault")
    suspend fun getMarketVault(
        @Path("marketId") marketId: String
    ): GetMarketVaultResponse

    @GET("api/v2/markets/{marketId}/creator-fees")
    suspend fun getCreatorFees(
        @Path("marketId") marketId: String
    ): CreatorFeesData

    @GET("api/v1/users/{address}/participated-markets")
    suspend fun getParticipatedMarkets(
        @Path("address") address: String
    ): GetMarketsResponse

    @GET("api/v1/markets/{marketId}/chat")
    suspend fun getChatMessages(
        @Path("marketId") marketId: String,
        @Query("limit") limit: Int = 100
    ): GetChatMessagesResponse

    @POST("api/v1/markets/{marketId}/chat")
    suspend fun addChatMessage(
        @Path("marketId") marketId: String,
        @Body request: AddChatMessageRequest
    ): AddChatMessageResponse

    @POST("api/v1/generate-instruction")
    suspend fun generateInstruction(
        @Body request: GenerateInstructionRequest
    ): GenerateInstructionResponse
}