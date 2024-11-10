package com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.navigation.Routes
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components.OutlinedCardTitle
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.components.WorkOutElement
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingProgressScreen(navController: NavController, trainingId: Int) {
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
                            navController.navigate(Routes.CREATE_UPDATE_TRAINING) },
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
                        onClick = { // Добавить логику удаления тренировки
                            navController.navigate(Routes.TRAINING_LIST)
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
                onClick = { // Добавить логику сохранения в выполненные тренировки
                    navController.navigate(Routes.TRAINING_LIST)
                },
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
                OutlinedCardTitle()
                LazyColumn {
                    items(20) {
                        WorkOutElement()
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
