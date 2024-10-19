package com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Fields(mutableSubject: String, onSubjectChange: (String) -> Unit, mutableMessage: String, onMessageChange: (String) -> Unit) {

    OutlinedTextField(
        value = mutableSubject,
        singleLine = true,
        onValueChange = { onSubjectChange(it) },
        label = { Text("Ошибка") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 15.dp)
    )
    OutlinedTextField(
        value = mutableMessage,
        onValueChange = { onMessageChange(it) },
        label = { Text("Описание") },
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(start = 12.dp, end = 12.dp)
    )
}