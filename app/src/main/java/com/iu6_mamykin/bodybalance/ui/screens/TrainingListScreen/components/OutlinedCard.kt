package com.iu6_mamykin.bodybalance.ui.screens.TrainingListScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.navigation.Routes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OutlinedCard(
    navController: NavController,
    trainingId: Int,
    trainingName: String,
    dateTime: Date
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(12),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 9.dp, start = 12.dp, end = 12.dp)
            .clickable { navController.navigate(Routes.trainingProgressWithArgs(trainingId.toString())) }
    ) {
        Text(
            text = trainingName,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = SimpleDateFormat("dd.MM.yyyy  |  HH:mm", Locale.getDefault()).format(dateTime),
            modifier = Modifier
                .padding(top = 4.dp, start = 16.dp, bottom = 16.dp, end = 16.dp),
            fontSize = 14.sp
        )
    }
}