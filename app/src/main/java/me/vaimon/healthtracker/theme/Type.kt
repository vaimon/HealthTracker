package me.vaimon.healthtracker.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.vaimon.healthtracker.R

val figTreeFamily = FontFamily(
    Font(R.font.figtree)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = figTreeFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = figTreeFamily,
        fontWeight = FontWeight.Black,
        fontSize = 32.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = figTreeFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = figTreeFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
)