package com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components.OutlinedCardTitle
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components.WorkOutElement
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateTrainingScreen() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            painterResource(R.drawable.arrow_back),
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = { /* do something */ },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenColor)
                    ) {
                        Text("Сохранить")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* do something */ },
                icon = { Icon(Icons.Filled.Check, "Выполнена") },
                text = { Text(text = "Выполнена") },
                containerColor = GreenColor,
                contentColor = WhiteColor
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var mutableTitle by remember { mutableStateOf(" ") }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor,
                        focusedLabelColor = BlackColor
                    ),
                    value = mutableTitle,
                    singleLine = true,
                    onValueChange = { mutableTitle = it },
                    label = { Text("Название") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 15.dp)
                )
                //var mutableDate by remember { mutableStateOf(" ") }
                var showDatePicker by remember { mutableStateOf(false) }
                val datePickerState = rememberDatePickerState()
                val selectedDate = datePickerState.selectedDateMillis?.let {
                    convertMillisToDate(it)
                } ?: " "
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor,
                        focusedLabelColor = BlackColor
                    ),
                    value = selectedDate,
                    singleLine = true,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Дата") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 15.dp),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_icon),
                            contentDescription = "Выбор даты",
                            modifier = Modifier
                                .clickable {
                                    showDatePicker = !showDatePicker
                                }
                                .padding(12.dp)
                        )
                    }
                )
                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = !showDatePicker },
                        confirmButton = {
                            TextButton(onClick = { showDatePicker = !showDatePicker }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = !showDatePicker }) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
                var mutableTime by remember { mutableStateOf(" ") }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor,
                        focusedLabelColor = BlackColor
                    ),
                    value = mutableTime,
                    singleLine = true,
                    onValueChange = { mutableTime = it },
                    label = { Text("Время") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 15.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CreateUpdateTrainingScreenPreview() {
    BodyBalanceTheme {
        CreateUpdateTrainingScreen()
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}