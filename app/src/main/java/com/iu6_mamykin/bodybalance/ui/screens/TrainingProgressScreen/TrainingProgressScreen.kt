package com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.data.AppDatabase
import com.iu6_mamykin.bodybalance.data.entities.Workout
import com.iu6_mamykin.bodybalance.navigation.Routes
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components.OutlinedCardTitle
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components.WorkOutElement
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingProgressScreen(navController: NavController, database: AppDatabase, trainingId: Int) {
    // Состояния для хранения данных тренировки и упражнений
    val trainingNameState = remember { mutableStateOf<String?>(null) }
    val trainingIsCompleted = remember { mutableStateOf<Boolean?>(null) }
    val dateState = remember { mutableStateOf<Date?>(null) }
    val timeState = remember { mutableStateOf<Date?>(null) }
    val reminderState = remember { mutableStateOf<Date?>(null) }
    //val workoutsState = remember { mutableStateOf<List<Pair<String, Workout>>>(emptyList()) }
    val workoutsState = remember { mutableStateOf<List<Triple<Int, String, Workout>>>(emptyList()) }

    val isLoading = remember { mutableStateOf(true) }

    // Загружаем данные асинхронно
    LaunchedEffect(trainingId) {
        isLoading.value = true

        // Получаем тренировку по ID
        val training = database.trainingDao().getTrainingById(trainingId)
        val trainingNameId = training?.trainingNameId
        if (trainingNameId != null) {
            // Получаем название тренировки
            trainingNameState.value = database.trainingNameDao().getTrainingNameById(trainingNameId)
        }

        // Получаем дату, время, напоминание и упражнения
        dateState.value = training?.dateTime
        timeState.value = training?.dateTime
        reminderState.value = training?.reminderDateTime

        trainingIsCompleted.value = training?.isCompleted

        /*val workoutPairs = database.workoutDao().getWorkoutsByTrainingId(trainingId).map { workout ->
            val workoutName = database.workoutNameDao().getWorkoutNameById(workout.workoutNameId) ?: "Неизвестное упражнение"
            workoutName to workout
        }
        workoutsState.value = workoutPairs*/

        val workoutTriples =
            database.workoutDao().getWorkoutsByTrainingId(trainingId).map { workout ->
                val workoutName =
                    database.workoutNameDao().getWorkoutNameById(workout.workoutNameId)
                        ?: "Неизвестное упражнение"
                Triple(workout.workoutId, workoutName, workout)
            }
        workoutsState.value = workoutTriples

        // Завершаем загрузку
        isLoading.value = false
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val isTrainingCompleted = trainingIsCompleted.value == true

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
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
                    Button(
                        onClick = { // ДОБАВИТЬ ЛОГИКУ РЕДАКТИРОВАНИЯ КОНКРЕТНОЙ ТРЕНИРОВКИ
                            navController.navigate(Routes.createUpdateTrainingWithArgs(trainingId.toString()))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BlackColor)
                    ) {
                        Icon(
                            painterResource(R.drawable.edit_button),
                            contentDescription = "Редактировать",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Редактировать")
                    }
                    IconButton(
                        onClick = {
                            // Запускаем корутину для удаления тренировки
                            scope.launch {
                                // Удаляем тренировку по её ID из базы данных
                                val training = database.trainingDao().getTrainingById(trainingId)
                                training?.let {
                                    database.trainingDao().deleteTrainingById(trainingId)
                                }

                                // Отображаем Toast-сообщение с подтверждением удаления
                                Toast.makeText(
                                    context,
                                    "Тренировка удалена",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Переходим обратно к списку тренировок
                                navController.navigate(Routes.TRAINING_LIST)
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = DeleteButtonColor,
                            contentColor = WhiteColor
                        )
                    ) {
                        Icon(
                            painterResource(R.drawable.delete_button_second),
                            contentDescription = "Удалить"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    // Устанавливаем локально значение завершения тренировки
                    val newCompletionStatus = !isTrainingCompleted

                    // Обновляем локально значение завершения тренировки
                    trainingIsCompleted.value = newCompletionStatus
                    // Запускаем корутину для сохранения изменения в базе данных
                    scope.launch {
                        // Обновляем поле isCompleted для данной тренировки в базе данных
                        database.trainingDao().updateTrainingCompletionStatus(trainingId, newCompletionStatus)

                        // Отображаем Toast с соответствующим сообщением
                        val toastMessage = if (newCompletionStatus) {
                            "Поздравляем с окончанием тренировки!"
                        } else {
                            "Тренировка отмечена как не выполненная."
                        }
                        Toast.makeText(
                            context,
                            toastMessage,
                            Toast.LENGTH_SHORT
                        ).show()

                        // Переходим к следующему экрану или обновляем UI
                        navController.navigate(Routes.TRAINING_LIST)
                    }
                },
                icon = { Icon(if (isTrainingCompleted) Icons.Filled.Close else Icons.Filled.Check, "Выполнена") },
                text = { Text(text = if (isTrainingCompleted) "Не выполнена" else "Выполнена") },
                containerColor = if (isTrainingCompleted) DeleteButtonColor else GreenColor,
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
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    OutlinedCardTitle(
                        trainingName = trainingNameState.value ?: "Без названия",
                        date = dateState.value,
                        time = timeState.value,
                        reminder = reminderState.value
                    )
                    LazyColumn {
                        items(workoutsState.value.size) { index ->
                            val (workoutId, workoutName, workout) = workoutsState.value[index]

                            WorkOutElement(
                                workoutName = workoutName,
                                note = workout.note ?: "Нет заметок",
                                isCompleted = workout.isCompleted,
                                workoutId = workout.workoutId, // Передаем workoutId в WorkOutElement
                                workoutDao = database.workoutDao()
                            )
                        }
                    }
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun TrainingProgressScreenPreview() {
    BodyBalanceTheme {
        TrainingProgressScreen(navController: NavController)
    }
}*/
