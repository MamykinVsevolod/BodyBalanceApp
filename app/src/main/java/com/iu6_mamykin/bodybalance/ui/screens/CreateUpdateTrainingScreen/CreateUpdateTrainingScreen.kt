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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                var showTimePicker by remember { mutableStateOf(false) }
                val timePickerState = rememberTimePickerState()
                var selectedTime by remember { mutableStateOf(" ") }
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor,
                        focusedLabelColor = BlackColor
                    ),
                    value = selectedTime,
                    singleLine = true,
                    onValueChange = { /*mutableTime = it*/ },
                    label = { Text("Время") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 15.dp),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar_icon),
                            contentDescription = "Выбор времени",
                            modifier = Modifier
                                .clickable {
                                    showTimePicker = !showTimePicker
                                }
                                .padding(12.dp)
                        )
                    }
                )
                if (showTimePicker) {
                    AlertDialog(
                        title = {
                            Text(text = "Выбрать время", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        },
                        text = { TimePicker(state = timePickerState) },
                        onDismissRequest = { showTimePicker = !showTimePicker },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    // Получаем выбранные часы и минуты из TimePickerState
                                    val hours = timePickerState.hour
                                    val minutes = timePickerState.minute

                                    // Форматируем время в строку
                                    selectedTime = String.format("%02d:%02d", hours, minutes)
                                    showTimePicker = !showTimePicker
                                }) {
                                Text("OK")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showTimePicker = !showTimePicker }) {
                                Text("Отмена")
                            }
                        }
                    )
                }
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