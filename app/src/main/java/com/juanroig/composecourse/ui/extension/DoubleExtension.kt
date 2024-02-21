package com.juanroig.composecourse.ui.extension

import androidx.compose.ui.graphics.Color

fun Double.getColorByRating(): Color {
    return if (this >= 7.0) {
        Color(0xFF008000)
    } else if (this >= 5.0) {
        Color(0xFF808000)
    } else {
        Color(0xFF800000)
    }
}
