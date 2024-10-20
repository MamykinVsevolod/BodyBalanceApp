package com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateTrainingScreen() {
    val context = LocalContext.current

    var mutableTitle by remember { mutableStateOf(" ") }

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: " "

    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var selectedTime by remember { mutableStateOf(" ") }

    var checked by remember { mutableStateOf(false) }

    var workOutElements by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        painterResource(R.drawable.arrow_back), contentDescription = "Назад"
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
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                workOutElements = workOutElements + (" " to " ")
            },
            containerColor = BlackColor, contentColor = WhiteColor
        ) {
            Icon(
                painterResource(R.drawable.add_icon), contentDescription = "Добавить"
            )
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                item {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                        ),
                        value = mutableTitle,
                        singleLine = true,
                        onValueChange = { mutableTitle = it },
                        label = { Text("Название") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, end = 12.dp, bottom = 15.dp)
                    )
                }
                item {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
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
                            Icon(painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = "Выбор даты",
                                modifier = Modifier
                                    .clickable {
                                        showDatePicker = !showDatePicker
                                    }
                                    .padding(12.dp))
                        }
                    )
                    if (showDatePicker) {
                        DatePickerDialog(onDismissRequest = { showDatePicker = !showDatePicker },
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
                }
                item {
                    OutlinedTextField(colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
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
                            Icon(painter = painterResource(id = R.drawable.clock_icon),
                                contentDescription = "Выбор времени",
                                modifier = Modifier
                                    .clickable {
                                        showTimePicker = !showTimePicker
                                    }
                                    .padding(12.dp))
                        }
                    )
                    if (showTimePicker) {
                        AlertDialog(title = {
                            Text(
                                text = "Выбрать время",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                            text = { TimePicker(state = timePickerState) },
                            onDismissRequest = { showTimePicker = !showTimePicker },
                            confirmButton = {
                                TextButton(onClick = {
                                    val hours = timePickerState.hour
                                    val minutes = timePickerState.minute

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
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Switch(
                            checked = checked, onCheckedChange = {
                                checked = it
                            }, colors = SwitchDefaults.colors(
                                checkedTrackColor = BlackColor
                            )
                        )
                        Spacer(modifier = Modifier.size(9.dp))
                        AssistChip(
                            onClick = { },
                            label = { Text("Напоминание") },
                        )
                    }
                }
                item {
                    HorizontalDivider(modifier = Modifier.padding(start = 20.dp, end = 20.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(text = "Упражнения", fontSize = 20.sp)
                    }
                }
                items(workOutElements.size) { index ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                            ),
                            value = workOutElements[index].first,
                            singleLine = true,
                            onValueChange = { newName ->
                                workOutElements = workOutElements.toMutableList().also {
                                    it[index] = it[index].copy(first = newName)
                                }
                            },
                            label = { Text("Название") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 12.dp, end = 12.dp, bottom = 5.dp)
                        )
                        IconButton(
                            onClick = {
                                workOutElements = workOutElements.toMutableList().also {
                                    it.removeAt(index)
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = DeleteButtonColor, contentColor = WhiteColor
                            ),
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            Icon(
                                painterResource(R.drawable.delete_button_second),
                                contentDescription = "Удалить"
                            )
                        }
                    }
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                        ),
                        value = workOutElements[index].second,
                        onValueChange = { newNote ->
                            workOutElements = workOutElements.toMutableList().also {
                                it[index] = it[index].copy(second = newNote)
                            }
                        },
                        label = { Text("Заметки") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(94.dp)
                            .padding(start = 12.dp, end = 12.dp)
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