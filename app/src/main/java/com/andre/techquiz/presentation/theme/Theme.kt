package com.andre.techquiz.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val Roboto = FontFamily.Default // en Android el sistema usa Roboto

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Bold, fontSize = 64.sp, lineHeight = 72.sp),
    displaySmall = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Bold, fontSize = 36.sp, lineHeight = 42.sp),
    headlineLarge = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 34.sp),
    headlineMedium = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 28.sp),
    titleLarge = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Medium, fontSize = 21.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 20.sp),
    titleSmall = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Bold, fontSize = 14.sp, lineHeight = 16.sp),
    bodyLarge = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 20.sp),
    bodyMedium = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 14.sp),
    labelLarge = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 16.sp),
    labelSmall = TextStyle(fontFamily = Roboto, fontWeight = FontWeight.Bold, fontSize = 12.sp, lineHeight = 14.sp)
)