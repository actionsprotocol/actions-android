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
    }

    interface BorderColors {
        val Primary: Color
        val Secondary: Color
    }

    val Core: CoreColors
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
        override val Primary = Color(0xFF1A1A1A)
        override val PrimaryInversed = Color(0xFFFFFFFF)
        override val Tertiary = Color(0xFF9FA5AC)
        override val Secondary = Color(0xFF000000).copy(alpha = 0.5f)
        override val Disabled = Color(0xFF000000).copy(alpha = 0.2f)
    }

    override val Background = object : AppColors.BackgroundColors {
        override val Primary = Color(0xFFFFFFFF)
    }

    override val Border = object : AppColors.BorderColors {
        override val Primary = Color(0xFFEBEBEB)
        override val Secondary = Color(0xFF000000).copy(alpha = .5f)
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
        override val PrimaryInversed = Color(0xFF1A1A1A)
        override val Secondary = Color(0xFFFFFFFF).copy(alpha = 0.5f)
        override val Tertiary = Color(0xFF818181)
        override val Disabled = Color(0xFF2ED159).copy(alpha = 0.2f)
    }

    override val Background = object : AppColors.BackgroundColors {
        override val Primary = Color(0xFF000000)
    }

    override val Border = object : AppColors.BorderColors {
        override val Primary = Color(0xFFEBEBEB)
        override val Secondary = Color(0xFFFFFFFF).copy(alpha = .5f)
    }
}
