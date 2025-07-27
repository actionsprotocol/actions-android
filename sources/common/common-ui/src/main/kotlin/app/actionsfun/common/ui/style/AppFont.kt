package app.actionsfun.common.ui.style

import app.actionsfun.common.ui.R
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalTextApi::class)
val rubikFamily = FontFamily(
    Font(R.font.rubik_light, weight = FontWeight.Light, variationSettings = FontVariation.Settings(FontWeight.Light, FontStyle.Normal)),
    Font(R.font.rubik_regular, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontWeight.Normal, FontStyle.Normal)),
    Font(R.font.rubik_italic, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontWeight.Normal, FontStyle.Italic)),
    Font(R.font.rubik_medium, weight = FontWeight.Medium, variationSettings = FontVariation.Settings(FontWeight.Medium, FontStyle.Normal)),
    Font(R.font.rubik_semibold, weight = FontWeight.SemiBold, variationSettings = FontVariation.Settings(FontWeight.SemiBold, FontStyle.Normal)),
    Font(R.font.rubik_bold, weight = FontWeight.Bold, variationSettings = FontVariation.Settings(FontWeight.Bold, FontStyle.Normal)),
    Font(R.font.rubik_extrabold, weight = FontWeight.ExtraBold, variationSettings = FontVariation.Settings(FontWeight.ExtraBold, FontStyle.Normal)),
    Font(R.font.rubik_black, weight = FontWeight.Black, variationSettings = FontVariation.Settings(FontWeight.Black, FontStyle.Normal)),
)