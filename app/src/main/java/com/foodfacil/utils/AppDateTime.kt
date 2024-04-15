package com.foodfacil.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

class AppDateTime{

    @RequiresApi(Build.VERSION_CODES.O)

    fun getMiliseconds(date: Instant): Long {
        val formatter = DateTimeFormatter.ISO_INSTANT
        val instant = Instant.parse(date.toString())

        // Convert to milliseconds since epoch
        return instant.toEpochMilliseconds()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBrazilianDate(date: Instant): String? {
        // Define o formato desejado para a data
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        // Exemplo: Converter milissegundos para LocalDateTime
        val milliseconds = getMiliseconds(date)
        val instant = Instant.fromEpochMilliseconds(milliseconds)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        // Converter para LocalDateTime do Java para usar o método toInstant()
        val javaLocalDateTime = localDateTime.toJavaLocalDateTime()

        // Formatar a data usando o formatter especificado
        val formattedDate = formatter.format(javaLocalDateTime)

        return formattedDate;
    }
}