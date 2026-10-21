package com.andre.techquiz.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),        // etiqueta de la pregunta
    medium = RoundedCornerShape(12.dp),      // chips, campos, estadísticas
    large = RoundedCornerShape(16.dp),       // tarjetas
    extraLarge = RoundedCornerShape(20.dp)   // tarjeta de puntaje final
)