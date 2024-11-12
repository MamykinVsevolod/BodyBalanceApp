package com.iu6_mamykin.bodybalance.ui.screens.ProfileEditScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.data.AppDatabase
import com.iu6_mamykin.bodybalance.data.Gender
import com.iu6_mamykin.bodybalance.data.entities.User
import com.iu6_mamykin.bodybalance.navigation.MyNavigationBar
import com.iu6_mamykin.bodybalance.navigation.Routes
import com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen.convertMillisToDate
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(navController: NavController, database: AppDatabase) {
    var mutableName by remember { mutableStateOf("") }
    var mutableEmail by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf(" ") }
    LaunchedEffect(datePickerState.selectedDateMillis) {
        selectedDate = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: " "  // Обновляем дату при изменении selectedDateMillis
    }
    var selectedGender by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()

    val isLoading = remember { mutableStateOf(true) }

    // Загрузка данных из базы данных
    LaunchedEffect(Unit) {
        scope.launch {
            isLoading.value = true
            val user = database.userDao().getUser()
            user?.let {
                mutableName = it.name
                mutableEmail = it.email
                selectedDate =
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it.birthDate)
                selectedGender = if (it.gender == Gender.MALE) 0 else 1
                datePickerState.selectedDateMillis = it.birthDate.time
            }
            isLoading.value = false
        }
    }
    val context = LocalContext.current
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
                    IconButton(onClick = { navController.popBackStack() }) {
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
        bottomBar = { MyNavigationBar(navController = navController, 1) }
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
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    } else {
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
                                isEmailValid = emailPattern.matches(it)
                            },
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
                            DatePickerDialog(onDismissRequest = {
                                showDatePicker = !showDatePicker
                            },
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
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            // Проверяем поля перед сохранением
                            if (mutableName.isBlank()) {
                                Toast.makeText(context, "Введите имя", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (!isEmailValid || mutableEmail.isBlank()) {
                                Toast.makeText(context, "Введите корректный адрес электронной почты", Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            if (selectedDate.isBlank()) {
                                Toast.makeText(context, "Выберите дату рождения", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            // Сохранение данных в базу данных
                            scope.launch {
                                val user = User(
                                    name = mutableName,
                                    email = mutableEmail,
                                    birthDate = Date(datePickerState.selectedDateMillis ?: 0),
                                    gender = if (selectedGender == 0) Gender.MALE else Gender.FEMALE
                                )
                                database.userDao().insertSingleUser(user)
                                Toast.makeText(context, "Данные профиля сохранены!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenColor)
                    ) {
                        Text("Сохранить")
                    }
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun ProfileEditScreenPreview() {
    BodyBalanceTheme {
        ProfileEditScreen(navController: NavController)
    }
}*/
