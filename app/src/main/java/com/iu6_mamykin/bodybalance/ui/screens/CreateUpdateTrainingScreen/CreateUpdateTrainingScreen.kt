package com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen

import android.content.Context
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.data.AppDatabase
import com.iu6_mamykin.bodybalance.data.entities.Training
import com.iu6_mamykin.bodybalance.data.entities.TrainingName
import com.iu6_mamykin.bodybalance.data.entities.Workout
import com.iu6_mamykin.bodybalance.data.entities.WorkoutName
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateTrainingScreen(navController: NavController, database: AppDatabase) {
    val context = LocalContext.current

    // для НАЗВАНИЯ
    var mutableTitle by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options =
        listOf("Кардио-тренировка", "Моя в зале", "В зале с Дашей") // Замените на свои значения
    val filteredOptions = options.filter { it.contains(mutableTitle, ignoreCase = true) }

    // для ДАТЫ
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: " "

    // для даты НАПОМИНАНИЯ
    var showDateNotifyPicker by remember { mutableStateOf(false) }
    val datePickerNotifyState = rememberDatePickerState()
    val selectedNotifyDate = datePickerNotifyState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: " "

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
    var workOutElements by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    val workOutOptions = listOf("Упражнение 1", "Упражнение 2", "Упражнение 3")

    val coroutineScope = rememberCoroutineScope()
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
                                        mutableTitle = option  // подставляем выбранный текст в поле
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
                                onDismissRequest = { showTimeNotifyPicker = !showTimeNotifyPicker },
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
                                            workOutElements = workOutElements.toMutableList().also {
                                                it[index] = it[index].copy(first = workOutOptions)
                                            }
                                            expandedWorkOut = false
                                        }
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = {
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

