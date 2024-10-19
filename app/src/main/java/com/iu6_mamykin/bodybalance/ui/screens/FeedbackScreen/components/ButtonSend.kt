package com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor

@Composable
fun ButtonSend(onOpenAlertDialogChange: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.fillMaxWidth()
    ) {
        androidx.compose.material3.Button(
            onClick = onOpenAlertDialogChange,
            colors = ButtonDefaults.buttonColors(containerColor = GreenColor)
        ) {
            Icon(
                painterResource(R.drawable.done_button),
                contentDescription = "Отправить",
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Отправить")
        }
    }
}