package app.actionsfun.environment

import app.actionsfun.environment.config.BuildConfigField
import app.actionsfun.environment.config.debugBuildConfig
import app.actionsfun.environment.config.releaseBuildConfig

interface Environment {

    val versionCode: Int
        get() = System.getenv().getOrDefault("versionCode", "1").toInt()

    val versionName: String
        get() = System.getenv().getOrDefault("versionName", "1.0.0").orEmpty()

    val ci: Boolean
        get() = System.getenv().getOrDefault("CI", "false").toBoolean()

    val signing: Signing

    val buildConfig: List<BuildConfigField>

    private class Debug : Environment {
        override val signing: Signing = Signing.debug
        override val buildConfig: List<BuildConfigField> = debugBuildConfig
    }

    private class Production : Environment {
        override val signing: Signing = Signing.release
        override val buildConfig: List<BuildConfigField> = releaseBuildConfig
    }

    companion object {
        val debug: Environment = Debug()
        val production: Environment = Production()
        val default: Environment = Debug()
    }
}
