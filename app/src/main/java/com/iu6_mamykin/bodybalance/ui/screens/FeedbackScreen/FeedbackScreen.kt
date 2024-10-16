package com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.MyNavigationBar
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.OutlinedCard
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.SegmentedButton
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen() {
    val context = LocalContext.current
    var mutableSubject by remember { mutableStateOf(" ") }
    var mutableMessage by remember { mutableStateOf(" ") }
    var openAlertDialog by remember { mutableStateOf(false) }

    val email = "support_bodybalance_app@gmail.com"

    when {
        openAlertDialog -> {
            CustomAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    openAlertDialog = false
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        type = "text/plain"
                        data =
                            Uri.parse("mailto:$email?subject=$mutableSubject&body=$mutableMessage")
                    }

                    try {
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "Произошла ошибка", Toast.LENGTH_SHORT).show()
                    }
                },
                dialogTitle = "Отправка",
                dialogText = "Для отправки сообщения об ошибке будет использовано почтовое приложение"
            )
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Отчет об ошибке") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = { MyNavigationBar(2) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = mutableSubject,
                        singleLine = true,
                        onValueChange = { mutableSubject = it },
                        label = { Text("Ошибка") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, bottom = 15.dp)
                    )
                    OutlinedTextField(
                        value = mutableMessage,
                        onValueChange = { mutableMessage = it },
                        label = { Text("Описание") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .padding(start = 12.dp, end = 12.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { openAlertDialog = true },
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
        }
    }
}

@Preview
@Composable
fun FeedbackScreenPreview() {
    BodyBalanceTheme {
        FeedbackScreen()
    }
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("ОК")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Отмена")
            }
        }
    )
}