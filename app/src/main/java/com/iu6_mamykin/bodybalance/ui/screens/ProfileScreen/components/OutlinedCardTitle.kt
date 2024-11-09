package com.iu6_mamykin.bodybalance.ui.screens.ProfileScreen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components.TextComponent

@Composable
fun OutlinedCardTitle() {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(20),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp, start = 12.dp, end = 13.dp, bottom = 13.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Дарья",
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 11.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        TextComponent(
            header = "Почта", value = "darya.thebest@gmail.com"
        )
        TextComponent(
            header = "Пол", value = "Женский"
        )
        TextComponent(
            header = "Дата рождения", value = "30.04.2024"
        )
        Spacer(modifier = Modifier.padding(bottom = 11.dp))
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar_icon),
            contentDescription = "Фото профиля",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(89.dp)
                .clip(CircleShape)
        )
    }
}