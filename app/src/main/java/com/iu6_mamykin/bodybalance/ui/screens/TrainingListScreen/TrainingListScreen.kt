package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.data.AppDatabase
import com.iu6_mamykin.bodybalance.data.entities.Training
import com.iu6_mamykin.bodybalance.navigation.MyNavigationBar
import com.iu6_mamykin.bodybalance.navigation.Routes
import com.iu6_mamykin.bodybalance.ui.screens.TrainingListScreen.components.OutlinedCard
import com.iu6_mamykin.bodybalance.ui.screens.TrainingListScreen.components.SegmentedButton
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingListScreen(navController: NavController, database: AppDatabase) {
    // Для хранения выбранного статуса
    val (selectedStatus, setSelectedStatus) = remember { mutableStateOf(false) } // false - Новые, true - Выполненные
    val (trainings, setTrainings) = remember {
        mutableStateOf<List<Pair<Training, String>>>(
            emptyList()
        )
    }
    val (isLoading, setIsLoading) = remember { mutableStateOf(true) } // Состояние загрузки данных

    // Загрузка данных при смене статуса
    LaunchedEffect(selectedStatus) {
        setIsLoading(true) // Устанавливаем состояние загрузки
        // Получаем список тренировок по статусу выполнения
        val trainingList = database.trainingDao().getTrainingsByCompletionStatus(selectedStatus)
        // Сопоставляем каждую тренировку с названием
        val trainingsWithNames = trainingList.map { training ->
            val trainingName =
                database.trainingNameDao().getTrainingNameById(training.trainingNameId)
            training to (trainingName ?: "Unknown")
        }
        setTrainings(trainingsWithNames)
        setIsLoading(false) // Завершаем загрузку
    }
    val trainingId = 1
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Тренировки") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Routes.createUpdateTrainingWithArgs((-1).toString())) },
                icon = {
                    Icon(
                        painterResource(R.drawable.add_icon), contentDescription = "Добавить"
                    )
                },
                text = { Text(text = "Добавить") },
                containerColor = GreenColor,
                contentColor = WhiteColor
            )
        },
        bottomBar = { MyNavigationBar(navController = navController, 0) }
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
                SegmentedButton(isCompleted = selectedStatus) { isCompleted ->
                    setSelectedStatus(isCompleted) // обновляем статус при выборе
                }
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyColumn {
                        items(trainings) { (training, trainingName) ->
                            OutlinedCard(
                                navController = navController,
                                trainingId = training.trainingId,
                                trainingName = trainingName,
                                dateTime = training.dateTime
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
fun TrainingScreenPreview() {
    BodyBalanceTheme {
        TrainingListScreen(navController: NavController)
    }
}*/
