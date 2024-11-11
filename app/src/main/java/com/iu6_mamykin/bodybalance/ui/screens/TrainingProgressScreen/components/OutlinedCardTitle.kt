package com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OutlinedCardTitle(
    trainingName: String,
    date: Date?,
    time: Date?,
    reminder: Date?
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(20),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 9.dp, start = 12.dp, end = 13.dp, bottom = 13.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = trainingName,
                modifier = Modifier
                    .padding(top = 7.dp, bottom = 11.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        TextComponent(
            header = "Дата", value = date?.let { formatDate(it) } ?: "-"
        )
        TextComponent(
            header = "Время", value = time?.let { formatTime(it) } ?: "-"
        )
        TextComponent(
            header = "Напоминание", value = SimpleDateFormat("dd.MM.yyyy  |  HH:mm", Locale.getDefault()).format(reminder) // reminder?.let { formatTime(it) } ?: "-"
        )
        Spacer(modifier = Modifier.padding(bottom = 11.dp))
    }
}

// Функции форматирования для даты и времени
fun formatDate(date: Date): String {
    val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return format.format(date)
}

fun formatTime(date: Date): String {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}