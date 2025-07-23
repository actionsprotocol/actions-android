package app.actionsfun.repository.user.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    val id: String,
    val anonymous: Boolean,
) {
    companion object {

        val Anonymous = User(
            id = UUID.randomUUID().toString(),
            anonymous = true,
        )
    }
}
