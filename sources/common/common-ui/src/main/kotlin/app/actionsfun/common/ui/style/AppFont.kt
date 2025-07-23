package app.actionsfun.common.ui.style

import app.actionsfun.common.ui.R
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalTextApi::class)
val dmmonoFamily = FontFamily(
    Font(R.font.dmmono_light, weight = FontWeight.Light, variationSettings = FontVariation.Settings(FontWeight.Light, FontStyle.Normal)),
    Font(R.font.dmmono_regular, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontWeight.Normal, FontStyle.Normal)),
    Font(R.font.dmmono_italic, weight = FontWeight.Normal, variationSettings = FontVariation.Settings(FontWeight.Normal, FontStyle.Italic)),
    Font(R.font.dmmono_medium, weight = FontWeight.Medium, variationSettings = FontVariation.Settings(FontWeight.Medium, FontStyle.Normal)),
)