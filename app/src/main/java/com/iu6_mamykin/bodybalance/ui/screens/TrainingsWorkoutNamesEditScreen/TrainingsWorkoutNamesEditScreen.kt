package com.iu6_mamykin.bodybalance.ui.screens.TrainingsWorkoutNamesEditScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.iu6_mamykin.bodybalance.data.entities.TrainingName
import com.iu6_mamykin.bodybalance.data.entities.WorkoutName
import com.iu6_mamykin.bodybalance.navigation.MyNavigationBar
import com.iu6_mamykin.bodybalance.ui.screens.TrainingsWorkoutNamesEditScreen.components.OutlinedCardTrainings
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsWorkoutNamesEditScreen(
    navController: NavController,
    database: AppDatabase,
    trainings: Boolean
) {
    // Состояние для хранения списка названий тренировок или упражнений
    var namesList by remember { mutableStateOf(listOf<String>()) }
    val isLoading = remember { mutableStateOf(true) }
    // Загружаем данные в зависимости от параметра `trainings`
    LaunchedEffect(trainings) {
        isLoading.value = true
        namesList = if (trainings) {
            // Загрузка названий тренировок
            database.trainingNameDao().getAllTrainingNames().map { it.name }
        } else {
            // Загрузка названий упражнений
            database.workoutNameDao().getAllWorkoutNames().map { it.name }
        }
        isLoading.value = false
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(if (trainings) "Названия тренировок" else "Названия упражнений")
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
                    LazyColumn {
                        items(namesList) { name ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedCardTrainings(
                                    text = name,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        if (trainings) {
                                            scope.launch {
                                                val trainingNameId = database.trainingNameDao()
                                                    .getTrainingNameIdByName(name)
                                                trainingNameId?.let {
                                                    // Удаляем элемент из базы данных
                                                    database.trainingNameDao().delete(
                                                        TrainingName(
                                                            trainingNameId = it,
                                                            name = name
                                                        )
                                                    )
                                                    // Обновляем список после удаления
                                                    namesList =
                                                        database.trainingNameDao()
                                                            .getAllTrainingNames()
                                                            .map { it.name }
                                                }
                                            }
                                        } else {
                                            scope.launch {
                                                val workOutNameId = database.workoutNameDao()
                                                    .getWorkoutNameIdByName(name)
                                                workOutNameId?.let {
                                                    // Удаляем элемент из базы данных
                                                    database.workoutNameDao().delete(
                                                        WorkoutName(
                                                            workoutNameId = it,
                                                            name = name
                                                        )
                                                    )
                                                    // Обновляем список после удаления
                                                    namesList =
                                                        database.workoutNameDao()
                                                            .getAllWorkoutNames()
                                                            .map { it.name }
                                                }
                                            }
                                        }
                                        Toast.makeText(context, "Информация удалена", Toast.LENGTH_SHORT).show()
                                    },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = DeleteButtonColor,
                                        contentColor = WhiteColor
                                    ),
                                    modifier = Modifier.padding(end = 12.dp)
                                ) {
                                    Icon(
                                        painterResource(R.drawable.delete_button_second),
                                        contentDescription = "Удалить"
                                    )
                                }
                            }
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
fun TrainingsWorkoutNamesEditScreenPreview() {
    BodyBalanceTheme {
        TrainingsWorkoutNamesEditScreen(false)
    }
}*/
