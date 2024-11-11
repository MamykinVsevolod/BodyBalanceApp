package com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iu6_mamykin.bodybalance.data.dao.WorkoutDao
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor

@Composable
fun WorkOutElement(
    workoutId: Int,
    workoutName: String,
    note: String,
    isCompleted: Boolean,
    workoutDao: WorkoutDao
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 6.dp, end = 6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkedState = remember { mutableStateOf(isCompleted) }
        LaunchedEffect(checkedState.value) {
            // После изменения состояния checkedState, обновляем состояние в базе данных
            workoutDao.updateWorkoutCompletion(workoutId, checkedState.value)
        }
        Checkbox(
            checked = checkedState.value,
            colors = CheckboxDefaults.colors(
                checkedColor = BlackColor
            ),
            onCheckedChange = { isChecked ->
                checkedState.value = isChecked
            })
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            shape = RoundedCornerShape(12),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Text(
                text = workoutName,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = note,
                modifier = Modifier
                    .padding(top = 4.dp, start = 16.dp, bottom = 16.dp, end = 16.dp),
                fontSize = 14.sp
            )
        }
    }
}