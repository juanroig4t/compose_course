package com.juanroig.composecourse.ui.extension

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val SIMPLE_REQUEST_DATE_FORMAT = "dd/MM/yyyy"
const val SIMPLE_DATE_FORMAT = "yyyy-MM-dd"

fun String.toSimpleDateFormat(): ZonedDateTime? {
    return try {
        // Crear un formateador para el formato "yyyy-MM-dd"
        val formatter = DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT)

        // Analizar la cadena de fecha en un objeto LocalDate
        val fechaLocal = LocalDate.parse(this, formatter)

        // Obtener la zona horaria actual (puedes ajustarla según tus necesidades)
        val zonaHoraria = ZoneId.systemDefault()

        // Construir un objeto ZonedDateTime utilizando la fecha local y la zona horaria
        ZonedDateTime.of(fechaLocal.atStartOfDay(), zonaHoraria)
    } catch (e: Exception) {
        null
    }
}

fun String.toYear(): String {
    val date = this.toSimpleDateFormat()
    return date?.year.toString()
}
