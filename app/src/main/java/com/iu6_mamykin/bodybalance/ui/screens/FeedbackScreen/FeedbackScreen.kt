package com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.data.AppDatabase
import com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen.components.ButtonSend
import com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen.components.CustomAlertDialog
import com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen.components.Fields
import com.iu6_mamykin.bodybalance.navigation.MyNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(navController: NavController, database: AppDatabase) {
    val context = LocalContext.current
    var openAlertDialog by remember { mutableStateOf(false) }
    var mutableSubject by remember { mutableStateOf(" ") }
    var mutableMessage by remember { mutableStateOf(" ") }

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
        bottomBar = { MyNavigationBar(navController = navController, 2) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp),
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
                    Fields(
                        mutableSubject = mutableSubject,
                        onSubjectChange = { mutableSubject = it },
                        mutableMessage = mutableMessage,
                        onMessageChange = { mutableMessage = it }
                    )
                }
                ButtonSend { openAlertDialog = true }
            }
        }
    }
}

/*@Preview
@Composable
fun FeedbackScreenPreview() {
    BodyBalanceTheme {
        FeedbackScreen(navController: NavController)
    }
}*/
