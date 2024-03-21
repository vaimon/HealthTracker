package me.vaimon.healthtracker.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.vaimon.healthtracker.R

val interTightFamily = FontFamily(
    Font(R.font.inter_tight)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = interTightFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = interTightFamily,
        fontWeight = FontWeight.Black,
        fontSize = 32.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = interTightFamily,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = interTightFamily,
        fontWeight = FontWeight.Black,
        fontSize = 16.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = interTightFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = interTightFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = interTightFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
    ),
)

val Typography.labelStub: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = interTightFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Grey
        )
    }

val Typography.labelSecondary: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = interTightFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Grey
        )
    }
val Typography.titleMediumSmall: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = interTightFamily,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
        )
    }