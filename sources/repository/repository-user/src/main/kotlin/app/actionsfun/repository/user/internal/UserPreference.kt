package app.actionsfun.repository.user.internal

import androidx.annotation.Keep
import app.actionsfun.repository.preferences.Preference
import app.actionsfun.repository.user.model.User

@Keep
internal data object UserPreference : Preference<User> {
    override val key: String = "Key:User"
    override val defaultValue = User.Anonymous
    override val serializer = User.serializer()
}
