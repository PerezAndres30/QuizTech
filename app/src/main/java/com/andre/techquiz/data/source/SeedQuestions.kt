package com.andre.techquiz.data.source

import com.andre.techquiz.data.model.Category
import com.andre.techquiz.data.model.Difficulty
import com.andre.techquiz.data.model.Question

val seedQuestions: List<Question> = listOf(
    Question(
        id = "seed-001",
        text = "¿Qué palabra clave declara una variable de solo lectura en Kotlin?",
        options = listOf("var", "val", "let", "const"),
        correctIndex = 1,
        category = Category.KOTLIN_ANDROID,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-002",
        text = "¿Qué archivo declara los permisos y componentes de una app Android?",
        options = listOf("strings.xml", "build.gradle", "MainActivity.kt", "AndroidManifest.xml"),
        correctIndex = 3,
        category = Category.KOTLIN_ANDROID,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-003",
        text = "¿Qué función de Compose conserva un valor entre recomposiciones?",
        options = listOf("remember", "mutableListOf", "LaunchedEffect", "setContent"),
        correctIndex = 0,
        category = Category.KOTLIN_ANDROID,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-004",
        text = "¿Qué hace el operador ?. en Kotlin?",
        options = listOf(
            "Fuerza que el valor no sea null",
            "Compara dos objetos por referencia",
            "Accede al miembro solo si el objeto no es null",
            "Declara un tipo genérico"
        ),
        correctIndex = 2,
        category = Category.KOTLIN_ANDROID,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-005",
        text = "¿Qué hace collectAsStateWithLifecycle() con un StateFlow?",
        options = listOf(
            "Lo convierte en State de Compose y deja de recolectar cuando la UI no está visible",
            "Lo guarda en SharedPreferences",
            "Lo ejecuta siempre en un hilo secundario",
            "Lo convierte en una lista mutable"
        ),
        correctIndex = 0,
        category = Category.KOTLIN_ANDROID,
        difficulty = Difficulty.HARD
    ),

    Question(
        id = "seed-006",
        text = "¿Qué estructura repite un bloque mientras se cumpla una condición?",
        options = listOf("import", "if", "return", "while"),
        correctIndex = 3,
        category = Category.PROGRAMACION,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-007",
        text = "¿Qué tipo de dato almacena verdadero o falso?",
        options = listOf("Int", "String", "Boolean", "Double"),
        correctIndex = 2,
        category = Category.PROGRAMACION,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-008",
        text = "¿Cuál es la complejidad de la búsqueda binaria?",
        options = listOf("O(n)", "O(log n)", "O(n²)", "O(1)"),
        correctIndex = 1,
        category = Category.PROGRAMACION,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-009",
        text = "¿Qué principio dice que una clase debe tener una sola razón para cambiar?",
        options = listOf(
            "Responsabilidad única",
            "Sustitución de Liskov",
            "Inversión de dependencias",
            "Abierto/cerrado"
        ),
        correctIndex = 0,
        category = Category.PROGRAMACION,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-010",
        text = "¿Qué es una condición de carrera (race condition)?",
        options = listOf(
            "Un hilo espera indefinidamente a otro",
            "Un programa usa más memoria de la disponible",
            "Dos hilos acceden a un recurso compartido y el resultado depende del orden de ejecución",
            "Una función se llama a sí misma sin condición de paro"
        ),
        correctIndex = 2,
        category = Category.PROGRAMACION,
        difficulty = Difficulty.HARD
    ),

    Question(
        id = "seed-011",
        text = "¿Qué sentencia SQL consulta datos de una tabla?",
        options = listOf("INSERT", "SELECT", "DELETE", "CREATE"),
        correctIndex = 1,
        category = Category.BASES_DE_DATOS,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-012",
        text = "¿Qué identifica de forma única cada fila de una tabla?",
        options = listOf("Clave foránea", "Vista", "Índice secundario", "Clave primaria"),
        correctIndex = 3,
        category = Category.BASES_DE_DATOS,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-013",
        text = "¿Qué cláusula filtra los grupos creados con GROUP BY?",
        options = listOf("WHERE", "ORDER BY", "HAVING", "LIMIT"),
        correctIndex = 2,
        category = Category.BASES_DE_DATOS,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-014",
        text = "¿Qué JOIN devuelve solo las filas que coinciden en ambas tablas?",
        options = listOf("LEFT JOIN", "RIGHT JOIN", "FULL JOIN", "INNER JOIN"),
        correctIndex = 3,
        category = Category.BASES_DE_DATOS,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-015",
        text = "¿Qué propiedad ACID garantiza que una transacción se aplica completa o no se aplica?",
        options = listOf("Atomicidad", "Consistencia", "Aislamiento", "Durabilidad"),
        correctIndex = 0,
        category = Category.BASES_DE_DATOS,
        difficulty = Difficulty.HARD
    ),

    Question(
        id = "seed-016",
        text = "¿Qué protocolo se usa para cargar páginas web?",
        options = listOf("FTP", "SMTP", "HTTP", "SSH"),
        correctIndex = 2,
        category = Category.REDES_SO,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-017",
        text = "¿Qué significan las siglas CPU?",
        options = listOf(
            "Unidad Central de Procesamiento",
            "Unidad de Control Periférico",
            "Procesador Central Universal",
            "Unidad de Cómputo Programable"
        ),
        correctIndex = 0,
        category = Category.REDES_SO,
        difficulty = Difficulty.EASY
    ),
    Question(
        id = "seed-018",
        text = "¿Cuántos bits tiene una dirección IPv4?",
        options = listOf("16", "32", "64", "128"),
        correctIndex = 1,
        category = Category.REDES_SO,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-019",
        text = "¿Qué hace el planificador (scheduler) de un sistema operativo?",
        options = listOf(
            "Traduce nombres de dominio a direcciones IP",
            "Cifra los datos del disco duro",
            "Decide qué proceso usa la CPU y por cuánto tiempo",
            "Administra los permisos de red"
        ),
        correctIndex = 2,
        category = Category.REDES_SO,
        difficulty = Difficulty.MEDIUM
    ),
    Question(
        id = "seed-020",
        text = "¿Cómo se llama la situación en que dos procesos esperan indefinidamente un recurso que tiene el otro?",
        options = listOf("Inanición", "Interbloqueo (deadlock)", "Fragmentación", "Paginación"),
        correctIndex = 1,
        category = Category.REDES_SO,
        difficulty = Difficulty.HARD
    )
)