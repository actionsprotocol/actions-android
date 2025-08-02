package app.actionsfun.repository.pinata.interactor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class PinataMetadata(
    val name: String,
    val image: String,
    val description: String
)