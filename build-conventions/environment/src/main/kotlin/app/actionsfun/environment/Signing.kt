package app.actionsfun.environment

class Signing(
    val storePassword: String,
    val keyAlias: String,
    val keyPassword: String
) {

    companion object {
        val debug: Signing
            get() = Signing(
                storePassword = "android",
                keyAlias = "androiddebugkey",
                keyPassword = "android"
            )

        val release: Signing
            get() = Signing(
                storePassword = "",
                keyAlias = "",
                keyPassword = ""
            )
    }
}
