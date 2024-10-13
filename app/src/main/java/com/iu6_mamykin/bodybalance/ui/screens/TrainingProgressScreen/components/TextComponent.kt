package com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextComponent(header: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 7.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = header,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(start = 17.dp)
        )
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Right,
            modifier = Modifier.padding(end = 17.dp)
        )
    }
}