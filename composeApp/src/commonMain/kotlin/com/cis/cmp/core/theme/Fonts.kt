package com.cis.cmp.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.poppins_bold
import cmp.composeapp.generated.resources.poppins_medium
import cmp.composeapp.generated.resources.poppins_regular
import org.jetbrains.compose.resources.Font

object Fonts {
    val Poppins
        @Composable get() = FontFamily(
            Font(
                resource = Res.font.poppins_regular,
                weight = FontWeight.Normal
            ), Font(
                resource = Res.font.poppins_medium,
                weight = FontWeight.Medium
            ), Font(
                resource = Res.font.poppins_bold,
                weight = FontWeight.Bold
            ),
        )
}

val Typography: Typography @Composable get() = Typography(
    displayLarge = TextStyle(
        fontFamily = Fonts.Poppins,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    )
)