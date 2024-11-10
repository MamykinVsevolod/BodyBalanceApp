package com.iu6_mamykin.bodybalance.ui.screens.Settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.navigation.Routes

@Composable
fun OutlinedCardTrainingsSettings(navController: NavController, text: String, isTraining: Boolean) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(12),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 9.dp, start = 12.dp, end = 12.dp, bottom = 14.dp)
            .clickable {
                navController.navigate(
                    Routes.trainingWorkoutNamesEditProgressWithArgs(
                        isTraining = isTraining
                    )
                )
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .padding(top = 28.dp, start = 16.dp, bottom = 28.dp),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            IconButton(
                onClick = {
                    navController.navigate(
                        Routes.trainingWorkoutNamesEditProgressWithArgs(
                            isTraining = isTraining
                        )
                    )
                }) {
                Icon(
                    painterResource(R.drawable.arrow_forward_icon),
                    contentDescription = "Вперед"
                )
            }
        }
    }
}