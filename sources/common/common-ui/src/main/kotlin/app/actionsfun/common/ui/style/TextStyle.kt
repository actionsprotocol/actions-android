package app.actionsfun.common.ui.style

import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

val Heading1 = textStyle(48.sp, FontWeight.Medium)
val Heading3 = textStyle(20.sp, FontWeight.Medium)

val Body18Medium = textStyle(18.sp, FontWeight.Medium)
val Body16Regular = textStyle(16.sp, FontWeight.Normal)
val Body16SemiBold = textStyle(16.sp, FontWeight.SemiBold)
val Body16Medium = textStyle(16.sp, FontWeight.Medium)
val Body14Medium = textStyle(14.sp, FontWeight.Medium)
val Body14SemiBold = textStyle(14.sp, FontWeight.SemiBold)
val Body14Regular = textStyle(14.sp, FontWeight.Normal)
val Body12Medium = textStyle(12.sp, FontWeight.Medium)
val Body12Regular = textStyle(12.sp, FontWeight.Normal)

fun textStyle(
    size: TextUnit,
    weight: FontWeight,
    fontFamily: FontFamily = rubikFamily,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle = FontStyle.Normal
) = TextStyle(
    fontSize = size,
    fontFamily = fontFamily,
    fontWeight = weight,
    fontStyle = fontStyle,
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    ),
    letterSpacing = letterSpacing
)

/**
 * Applies [TextDecoration] to current [TextStyle] without changing anything else
 */
fun TextStyle.applyDecoration(textDecoration: TextDecoration): TextStyle {
    return this.copy(textDecoration = textDecoration)
}

/**
 * Applies text color to current [TextStyle] without changing anything else
 */
fun TextStyle.applyColor(textColor: androidx.compose.ui.graphics.Color): TextStyle {
    return this.copy(color = textColor)
}