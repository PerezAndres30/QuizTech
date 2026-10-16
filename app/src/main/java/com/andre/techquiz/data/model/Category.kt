package com.andre.techquiz.data.model

import androidx.annotation.StringRes
import com.andre.techquiz.R

enum class Category(@StringRes val labelRes: Int) {
    KOTLIN_ANDROID(R.string.category_kotlin_android),
    PROGRAMACION(R.string.category_programacion),
    BASES_DE_DATOS(R.string.category_bases_de_datos),
    REDES_SO(R.string.category_redes_so)
}