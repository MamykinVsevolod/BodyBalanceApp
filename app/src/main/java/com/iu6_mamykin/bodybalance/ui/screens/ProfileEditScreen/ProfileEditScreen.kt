package com.iu6_mamykin.bodybalance.ui.screens.ProfileEditScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen.convertMillisToDate
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.MyNavigationBar
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen() {
    var mutableName by remember { mutableStateOf("") }

    var mutableEmail by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    // Регулярное выражение для валидации email
    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    // для ДАТЫ
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: " "

    var selectedGender by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text("Редактирование данных")
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        )
                    )
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
                    IconButton(
                        onClick = { /* do something */ },
                        modifier = Modifier.alpha(0f),
                        enabled = false,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = BlackColor,
                            contentColor = WhiteColor
                        )
                    ) {
                        Icon(
                            painterResource(R.drawable.settings_icon),
                            contentDescription = "Настройки"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        },
        bottomBar = { MyNavigationBar(1) }
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
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                    ),
                    value = if (mutableName == "") " " else mutableName,
                    onValueChange = { mutableName = it },
                    label = { Text("Имя") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                )
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                    ),
                    value = if (mutableEmail == "") " " else mutableEmail,
                    onValueChange = {
                        mutableEmail = it
                        isEmailValid = emailPattern.matches(it)},
                    label = { Text("Почта") },
                    isError = !isEmailValid,  // Устанавливаем isError, если email некорректен
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                )
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                    ),
                    value = selectedDate,
                    singleLine = true,
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Дата рождения") },
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 32.dp, end = 32.dp)
                ) {
                    Text(
                        text = "Пол:",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        RadioButton(
                            selected = selectedGender == 0,
                            onClick = { selectedGender = 0 },
                            colors = RadioButtonDefaults.colors(selectedColor = BlackColor)
                        )
                        Text(
                            text = "Мужской"
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedGender == 1,
                            onClick = { selectedGender = 1 },
                            colors = RadioButtonDefaults.colors(selectedColor = BlackColor)
                        )
                        Text(
                            text = "Женский"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileEditScreenPreview() {
    BodyBalanceTheme {
        ProfileEditScreen()
    }
}