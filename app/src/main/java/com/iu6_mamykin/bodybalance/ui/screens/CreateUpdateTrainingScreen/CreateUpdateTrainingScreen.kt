package com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.MainActivity
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.data.AppDatabase
import com.iu6_mamykin.bodybalance.data.entities.Training
import com.iu6_mamykin.bodybalance.data.entities.TrainingName
import com.iu6_mamykin.bodybalance.data.entities.Workout
import com.iu6_mamykin.bodybalance.data.entities.WorkoutName
import com.iu6_mamykin.bodybalance.navigation.Routes
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateTrainingScreen(
    navController: NavController,
    database: AppDatabase,
    trainingId: Int = -1
) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }
    // для НАЗВАНИЯ
    var mutableTitle by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val trainingNames = remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(Unit) {
        val trainingNamesList = database.trainingNameDao().getAllTrainingNames()
        trainingNames.value = trainingNamesList.map { it.name }
    }
    val options = trainingNames.value
    val filteredOptions = options.filter { it.contains(mutableTitle, ignoreCase = true) }

    // для ДАТЫ
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate by remember { mutableStateOf(" ") }  // Используем remember для состояния даты
    LaunchedEffect(datePickerState.selectedDateMillis) {
        selectedDate = datePickerState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: " "  // Обновляем дату при изменении selectedDateMillis
    }

    // для даты НАПОМИНАНИЯ
    var showDateNotifyPicker by remember { mutableStateOf(false) }
    val datePickerNotifyState = rememberDatePickerState()
    var selectedNotifyDate by remember { mutableStateOf(" ") }  // Используем remember для состояния даты напоминания
    LaunchedEffect(datePickerNotifyState.selectedDateMillis) {
        selectedNotifyDate = datePickerNotifyState.selectedDateMillis?.let {
            convertMillisToDate(it)
        } ?: " "  // Обновляем дату напоминания при изменении selectedDateMillis
    }
    var newTrainingId by remember { mutableStateOf(-1) } // НОВЫЙ АЙДИ ТРЕНИРОВКИ - РАССЧИТЫВАЕТСЯ ПОСЛЕ СОЗДАНИЯ ИЛИ РЕДАКТИРОВАНИЯ
    // для ВРЕМЕНИ
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var selectedTime by remember { mutableStateOf(" ") }

    // для времени НАПОМИНАНИЯ
    var showTimeNotifyPicker by remember { mutableStateOf(false) }
    val timePickerNotifyState = rememberTimePickerState()
    var selectedNotifyTime by remember { mutableStateOf(" ") }

    // для ВЫБОРА НАПОМИНАНИЯ
    var checked by remember { mutableStateOf(false) }

    // для УПРАЖНЕНИЙ
    /*var workOutElements by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    val workOutOptions = listOf("Упражнение 1", "Упражнение 2", "Упражнение 3")*/
    var workOutElements by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var workOutOptions by remember { mutableStateOf(listOf<String>()) } // только названия упражнений

    // Загружаем данные асинхронно
    LaunchedEffect(Unit) {
        // Получаем все записи из базы данных
        val workoutNamesList = database.workoutNameDao().getAllWorkoutNames()

        // Преобразуем их в список Pair (название упражнения, описание/идентификатор)
        //workOutElements = workoutNamesList.map { Pair(it.name, "Описание: ${it.name}") }

        // Заполняем workOutOptions только названиями упражнений
        workOutOptions = workoutNamesList.map { it.name }
    }
    val coroutineScope = rememberCoroutineScope()
    // Проверка trainingId для загрузки данных тренировки
    LaunchedEffect(trainingId) {
        if (trainingId >= 0) {
            isLoading.value = true
            // Получаем данные тренировки
            val training = database.trainingDao().getTrainingById(trainingId)

            training?.let {
                // Получаем название тренировки из TrainingNames
                val trainingName = database.trainingNameDao().getTrainingNameById(it.trainingNameId)
                mutableTitle = trainingName ?: ""  // Если null, оставляем пустую строку
                Log.d("Date", it.dateTime.time.toString())
                // Устанавливаем дату и время тренировки
                selectedDate = convertMillisToDate(it.dateTime.time)
                Log.d("Date", selectedDate)
                selectedTime = convertMillisToTime(it.dateTime.time)

                // Проверяем и устанавливаем дату и время напоминания, если оно есть
                it.reminderDateTime?.let { reminderDateTime ->
                    selectedNotifyDate = convertMillisToDate(reminderDateTime.time)
                    selectedNotifyTime = convertMillisToTime(reminderDateTime.time)
                    checked = true  // Включаем напоминание
                }

                newTrainingId = trainingId
                // Загружаем все упражнения, связанные с тренировкой
                val workouts = database.workoutDao().getWorkoutsByTrainingId(trainingId)
                workOutElements = workouts.map { workout ->
                    // Получаем имя упражнения
                    val workoutName =
                        database.workoutNameDao().getWorkoutNameById(workout.workoutNameId)
                    Pair(workoutName ?: "", workout.note ?: "")
                }
            }
            isLoading.value = false
        }
    }
    val createDelayedNotification = remember { mutableStateOf(false) }
    if (createDelayedNotification.value) {
        val titleNotification = mutableTitle
        val timeMessage = selectedTime
        val dateMessage = selectedDate
        val message = "$dateMessage | $timeMessage"
        val procedureId = newTrainingId

        // Получаем дату и время из процедуры
        val reminderDate = convertDateToMillis(selectedNotifyDate)
        val reminderTime = convertTimeToMillis(selectedNotifyTime)

        // Создаем LocalDateTime из даты и времени
        val reminderDateTime = if (reminderDate != null && reminderTime != null) {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(reminderDate + reminderTime),
                ZoneId.systemDefault()
            )
        } else null
        // Логирование времени перед передачей в scheduleNotification
        if (reminderDateTime != null) {
            scheduleNotification(context, titleNotification, message, reminderDateTime, procedureId)
        }
        createDelayedNotification.value = false
    }
    Log.d("SaveData", trainingId.toString())
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painterResource(R.drawable.arrow_back), contentDescription = "Назад"
                    )
                }
            },
            actions = {
                Button(
                    onClick = {
                        Log.d("SaveButton", "Button clicked")
                        coroutineScope.launch {
                            Log.d("SaveButton", "Calling onSaveButtonClicked")

                            // Если trainingId >= 0, это процесс редактирования
                            if (trainingId >= 0) {
                                // Удаление всех старых данных, связанных с этой тренировкой
                                deleteOldTrainingData(trainingId, database)

                                // Сохранение новой тренировки с новыми упражнениями
                                onSaveButtonClicked(
                                    mutableTitle,
                                    convertDateToMillis(selectedDate),
                                    selectedTime,
                                    checked,
                                    convertDateToMillis(selectedNotifyDate),
                                    selectedNotifyTime,
                                    workOutElements,
                                    database,
                                    context
                                )
                            } else {
                                // Если trainingId < 0, значит создаём новую тренировку
                                onSaveButtonClicked(
                                    mutableTitle,
                                    datePickerState.selectedDateMillis,
                                    selectedTime,
                                    checked,
                                    datePickerNotifyState.selectedDateMillis,
                                    selectedNotifyTime,
                                    workOutElements,
                                    database,
                                    context
                                )
                            }
                            val latestTraining = database.trainingDao().getLatestTraining()
                            if (latestTraining != null) {
                                newTrainingId = latestTraining.trainingId
                            }
                            createDelayedNotification.value = true
                            navController.navigate(Routes.TRAINING_LIST)
                        }

                    },
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
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    item {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 12.dp, bottom = 15.dp)
                        ) {
                            OutlinedTextField(
                                value = if (mutableTitle.isEmpty()) " " else mutableTitle,
                                onValueChange = {
                                    mutableTitle = it
                                    expanded = true  // раскрываем меню при вводе
                                },
                                label = { Text("Название") },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.dropdown_icon),
                                        contentDescription = "Dropdown Arrow",
                                        Modifier.clickable {
                                            expanded = true
                                        }  // раскрываем меню при нажатии на стрелку
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BlackColor,
                                    focusedLabelColor = BlackColor
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                filteredOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            mutableTitle =
                                                option  // подставляем выбранный текст в поле
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }
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
                        if (checked) {
                            OutlinedTextField(
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                                ),
                                value = selectedNotifyDate,
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
                                                showDateNotifyPicker = !showDateNotifyPicker
                                            }
                                            .padding(12.dp))
                                }
                            )
                            if (showDateNotifyPicker) {
                                DatePickerDialog(onDismissRequest = {
                                    showDateNotifyPicker = !showDateNotifyPicker
                                },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            showDateNotifyPicker = !showDateNotifyPicker
                                        }) {
                                            Text("OK")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {
                                            showDateNotifyPicker = !showDateNotifyPicker
                                        }) {
                                            Text("Cancel")
                                        }
                                    }
                                ) {
                                    DatePicker(state = datePickerNotifyState)
                                }
                            }
                            OutlinedTextField(colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BlackColor, focusedLabelColor = BlackColor
                            ),
                                value = selectedNotifyTime,
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
                                                showTimeNotifyPicker = !showTimeNotifyPicker
                                            }
                                            .padding(12.dp))
                                }
                            )
                            if (showTimeNotifyPicker) {
                                AlertDialog(title = {
                                    Text(
                                        text = "Выбрать время",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                    text = { TimePicker(state = timePickerNotifyState) },
                                    onDismissRequest = {
                                        showTimeNotifyPicker = !showTimeNotifyPicker
                                    },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            val hours = timePickerNotifyState.hour
                                            val minutes = timePickerNotifyState.minute

                                            selectedNotifyTime =
                                                String.format("%02d:%02d", hours, minutes)
                                            showTimeNotifyPicker = !showTimeNotifyPicker
                                        }) {
                                            Text("OK")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(onClick = {
                                            showTimeNotifyPicker = !showTimeNotifyPicker
                                        }) {
                                            Text("Отмена")
                                        }
                                    }
                                )
                            }
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
                            var expandedWorkOut by remember { mutableStateOf(false) }
                            val filteredOptionsWorkOut = workOutOptions.filter {
                                it.contains(
                                    workOutElements[index].first,
                                    ignoreCase = true
                                )
                            }

                            ExposedDropdownMenuBox(
                                expanded = expandedWorkOut,
                                onExpandedChange = { expandedWorkOut = !expandedWorkOut },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 12.dp, end = 12.dp, bottom = 5.dp)
                            ) {
                                OutlinedTextField(
                                    value = workOutElements[index].first,
                                    onValueChange = { newName ->
                                        workOutElements = workOutElements.toMutableList().also {
                                            it[index] = it[index].copy(first = newName)
                                        }
                                        expandedWorkOut = true  // открываем меню при вводе
                                    },
                                    label = { Text("Название") },
                                    trailingIcon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.dropdown_icon),
                                            contentDescription = "Dropdown Arrow",
                                            Modifier.clickable { expandedWorkOut = true }
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = BlackColor,
                                        focusedLabelColor = BlackColor
                                    ),
                                    singleLine = true,
                                    modifier = Modifier
                                        .menuAnchor()
                                        .fillMaxWidth()
                                )

                                ExposedDropdownMenu(
                                    expanded = expandedWorkOut,
                                    onDismissRequest = { expandedWorkOut = false },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    filteredOptionsWorkOut.forEach { workOutOptions ->
                                        DropdownMenuItem(
                                            text = { Text(workOutOptions, fontSize = 16.sp) },
                                            onClick = {
                                                workOutElements =
                                                    workOutElements.toMutableList().also {
                                                        it[index] =
                                                            it[index].copy(first = workOutOptions)
                                                    }
                                                expandedWorkOut = false
                                            }
                                        )
                                    }
                                }
                            }

                            IconButton(
                                onClick = {
                                    if (trainingId >= 0) {
                                        coroutineScope.launch {
                                            // Получаем workoutNameId из таблицы WorkoutNames по названию
                                            val workoutNameId = database.workoutNameDao().getWorkoutNameIdByName(workOutElements[index].first)

                                            // Получаем заметку из элемента списка
                                            val note = workOutElements[index].second

                                            // Если найден workoutNameId, продолжаем искать и удалять
                                            workoutNameId?.let {
                                                // Получаем все упражнения с данным workoutNameId и заметкой
                                                val workoutsToDelete = database.workoutDao().getWorkoutsByNameAndNote(workoutNameId, note)

                                                // Удаляем все найденные записи
                                                workoutsToDelete.forEach { workout ->
                                                    database.workoutDao().delete(workout)
                                                }
                                            }
                                        }
                                    }
                                    workOutElements = workOutElements.toMutableList().also {
                                        it.removeAt(index)
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = DeleteButtonColor,
                                    contentColor = WhiteColor
                                ),
                                modifier = Modifier.padding(end = 12.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.delete_button_second),
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
}

/*
@Preview
@Composable
fun CreateUpdateTrainingScreenPreview() {
    BodyBalanceTheme {
        CreateUpdateTrainingScreen(navController: NavController)
    }
}
*/

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

fun convertMillisToTime(millis: Long): String {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = millis
    }
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    val minutes = calendar.get(Calendar.MINUTE)
    return String.format("%02d:%02d", hours, minutes)
}

fun convertDateToMillis(dateString: String): Long? {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return try {
        formatter.parse(dateString)?.time
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}
fun convertTimeToMillis(timeString: String): Long? {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return try {
        val parsedDate = formatter.parse(timeString)
        parsedDate?.time
    } catch (e: ParseException) {
        e.printStackTrace()
        null
    }
}


suspend fun onSaveButtonClicked(
    title: String,
    dateMillis: Long?,
    time: String,
    hasReminder: Boolean,
    reminderDateMillis: Long?,
    reminderTime: String,
    workoutItems: List<Pair<String, String>>,
    database: AppDatabase,
    context: Context
) {
    // Проверка на заполнение всех необходимых полей
    if (title.isBlank() || dateMillis == null || time.isBlank()) {
        if (title.isBlank()) Log.d("Validation", "Title is blank")
        if (dateMillis == null) Log.d("Validation", "Date is null")
        if (time.isBlank()) Log.d("Validation", "Time is blank")
        Toast.makeText(context, "Введите название, дату и время тренировки", Toast.LENGTH_SHORT)
            .show()
        return
    }

// Преобразуем `dateMillis` в `Date`
    val date = Date(dateMillis)

// Получаем `trainingNameId` по названию из базы данных (в корутине)
    val trainingNameId = withContext(Dispatchers.IO) {
        database.trainingNameDao().getTrainingNameIdByName(title)
    }

// Если тренировка с таким названием не найдена, добавляем в таблицу TrainingNames
    val finalTrainingNameId = trainingNameId ?: withContext(Dispatchers.IO) {
        val newTrainingNameId = database.trainingNameDao().insert(TrainingName(name = title))
        newTrainingNameId.toInt() // Возвращаем ID новой записи
    }

// Объединяем дату и время тренировки
    val dateTime = combineDateAndTime(date, time)

// Если напоминание активно, объединяем дату и время напоминания, иначе оставляем `null`
    val reminderDateTime =
        if (hasReminder && reminderDateMillis != null && reminderTime.isNotBlank()) {
            combineDateAndTime(Date(reminderDateMillis), reminderTime)
        } else null

// Создаем и сохраняем тренировку
    val newTraining = Training(
        trainingNameId = finalTrainingNameId,
        dateTime = dateTime,
        reminderDateTime = reminderDateTime,
        isCompleted = false
    )

// Сохраняем тренировку (выполняем в корутине)
    val trainingId = withContext(Dispatchers.IO) {
        database.trainingDao().insert(newTraining).toInt()
    }

// Логируем ID тренировки для проверки
    Log.d("SaveButton", "Training Name ID: $finalTrainingNameId")

// Сохраняем упражнения, связанные с тренировкой (выполняем в корутине)
    workoutItems.forEach { (workoutTitle, note) ->
        // Проверяем, существует ли упражнение в таблице WorkoutNames
        val workoutNameId = withContext(Dispatchers.IO) {
            database.workoutNameDao().getWorkoutNameIdByName(workoutTitle)
        }

        // Если упражнение не найдено, добавляем его в таблицу WorkoutNames
        val finalWorkoutNameId = workoutNameId ?: withContext(Dispatchers.IO) {
            val newWorkoutNameId =
                database.workoutNameDao().insert(WorkoutName(name = workoutTitle))
            newWorkoutNameId.toInt() // Возвращаем ID нового упражнения
        }

        // Создаем новое упражнение для тренировки
        val newWorkout = Workout(
            trainingId = trainingId,
            workoutNameId = finalWorkoutNameId,
            note = note,
            isCompleted = false
        )

        // Сохраняем упражнение в базе данных (выполняем в корутине)
        withContext(Dispatchers.IO) {
            database.workoutDao().insert(newWorkout)
        }
    }
    Toast.makeText(context, "Тренировка сохранена!", Toast.LENGTH_SHORT).show()
}

fun combineDateAndTime(date: Date, timeString: String): Date {
    val calendar = Calendar.getInstance().apply {
        this.time = date  // Указываем, что это свойство `Calendar`, а не параметр функции
        val parts = timeString.split(":")
        set(Calendar.HOUR_OF_DAY, parts[0].toInt())
        set(Calendar.MINUTE, parts[1].toInt())
    }
    return calendar.time
}

suspend fun deleteOldTrainingData(trainingId: Int, database: AppDatabase) {
    // Удаляем все упражнения, связанные с этой тренировкой
    val workouts = withContext(Dispatchers.IO) {
        database.workoutDao().getWorkoutsByTrainingId(trainingId)
    }

    workouts.forEach { workout ->
        withContext(Dispatchers.IO) {
            database.workoutDao().delete(workout)
        }
    }

    // Удаляем саму тренировку
    withContext(Dispatchers.IO) {
        val training = database.trainingDao().getTrainingById(trainingId)
        if (training != null) {
            database.trainingDao().delete(training)
        }
    }

    // Логируем удаление данных для проверки
    Log.d("DeleteOldData", "Old data for trainingId $trainingId has been deleted.")
}


fun scheduleNotification(context: Context, title: String, message: String, notificationTime: LocalDateTime, notificationId: Int) {
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("notification_title", title)
        putExtra("notification_message", message)
        putExtra("notification_id", notificationId) // Добавим notificationId в intent
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val systemZoneId = ZoneId.systemDefault()
    val systemZonedDateTime = notificationTime.atZone(systemZoneId)
    val triggerAtMillis = systemZonedDateTime.toInstant().toEpochMilli()

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
}
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "notification_channel1"
        val channelName = "My Notification Channel1"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel description"
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("notification_title") ?: "Тренировка"
        val message = intent.getStringExtra("notification_message") ?: "Время выполнить тренировку"
        val notificationId = intent.getIntExtra("notification_id", 0) // Получим notificationId из intent

        // Создаем намерение для запуска MainActivity с необходимым маршрутом
        val resultIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination_route", "${Routes.trainingProgressWithArgs(notificationId.toString())}/$notificationId")
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "notification_channel1")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) // Устанавливаем PendingIntent для уведомления
            .setAutoCancel(true) // Автоматическое закрытие уведомления после нажатия
            .build()

        notificationManager.notify(notificationId, notification) // Используем notificationId для уведомления
    }
}