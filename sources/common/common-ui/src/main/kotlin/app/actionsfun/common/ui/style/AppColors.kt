package app.actionsfun.common.ui.style

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

interface AppColors {
    interface CoreColors {
        val Primary: Color
        val Secondary: Color
        val Accent: Color
    }

    interface TextColors {
        val Primary: Color
        val PrimaryInversed: Color
        val Secondary: Color
        val Tertiary: Color
        val Disabled: Color
    }

    interface BackgroundColors {
        val Primary: Color
        val Surface: Color
    }

    interface ExtensionColors {
        val Pink: Color
        val Green: Color
        val Red: Color
    }

    interface BorderColors {
        val Primary: Color
        val Secondary: Color
    }

    val Core: CoreColors
    val Extension: ExtensionColors
    val Text: TextColors
    val Background: BackgroundColors
    val Border: BorderColors
}

object AppColorsLight : AppColors {
    override val Core = object : AppColors.CoreColors {
        override val Primary = Color(0xFFFFFFFF)
        override val Secondary = Color(0xFF000000)
        override val Accent = Color(0xFF2ED159)
    }

    override val Text = object : AppColors.TextColors {
        override val Primary = Color(0xFF151519)
        override val PrimaryInversed = Color(0xFFFFFFFF)
        override val Tertiary = Color(0xFF9FA5AC)
        override val Secondary = Color(0xFF727A84)
        override val Disabled = Color(0xFF000000).copy(alpha = 0.2f)
    }

    override val Background = object : AppColors.BackgroundColors {
        override val Primary = Color(0xFFFFFFFF)
        override val Surface = Color(0xFFFFFFFF)
    }

    override val Border = object : AppColors.BorderColors {
        override val Primary = Color(0xFFF4F4F6)
        override val Secondary = Color(0xFF000000).copy(alpha = .5f)
    }

    override val Extension = object : AppColors.ExtensionColors {
        override val Pink: Color = Color(0xFFEC58A9)
        override val Green: Color = Color(0xFF21D979)
        override val Red: Color = Color(0xFFF55B7F)
    }
}

object AppColorsDark : AppColors {
    override val Core = object : AppColors.CoreColors {
        override val Primary = Color(0xFF000000)
        override val Secondary = Color(0xFFFFFFFF)
        override val Accent = Color(0xFF2ED159)
    }

    override val Text = object : AppColors.TextColors {
        override val Primary = Color(0xFFFFFFFF)
        override val PrimaryInversed = Color(0xFF151519)
        override val Secondary = Color(0xFFB0B7BE)
        override val Tertiary = Color(0xFF6A7079)
        override val Disabled = Color(0xFFFFFFFF).copy(alpha = 0.2f)
    }

    override val Background = object : AppColors.BackgroundColors {
        override val Primary = Color(0xFF000000)
        override val Surface = Color(0xFF1A1A1A)
    }

    override val Border = object : AppColors.BorderColors {
        override val Primary = Color(0xFF2A2A2A)
        override val Secondary = Color(0xFFFFFFFF).copy(alpha = 0.3f)
    }

    override val Extension = object : AppColors.ExtensionColors {
        override val Pink: Color = Color(0xFFEC58A9)
        override val Green: Color = Color(0xFF21D979)
        override val Red: Color = Color(0xFFF55B7F)
    }
}