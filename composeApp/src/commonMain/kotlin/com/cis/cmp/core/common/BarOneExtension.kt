package com.cis.cmp.core.common

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.navigation.NavController

object BarOneExtension {

    fun Modifier.customGradientBackground(shape: Shape? = null): Modifier {
        val brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFFFFFFF), Color(0xFF007BFF), Color(0xFF0A0F6C),
                Color(0xFF0A0F6C), Color(0xFF007BFF), Color(0xFFFFFFFF)
            )
        )

        return if (shape != null) {
            // use shape if provided
            this.background(brush = brush, shape = shape)
        } else {
            // no shape parameter at all
            this.background(brush = brush)
        }
    }

}