package com.iu6_mamykin.bodybalance.ui.screens.TrainingsWorkoutNamesEditScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.MyNavigationBar
import com.iu6_mamykin.bodybalance.ui.screens.TrainingsWorkoutNamesEditScreen.components.OutlinedCardTrainings
import com.iu6_mamykin.bodybalance.ui.screens.TrainingsWorkoutNamesEditScreen.components.OutlinedCardWorkout
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import com.iu6_mamykin.bodybalance.ui.theme.DeleteButtonColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsWorkoutNamesEditScreen(trainings: Boolean) {
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
                if (trainings) {
                    LazyColumn {
                        items(20) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedCardTrainings(modifier = Modifier.weight(1f))
                                IconButton(
                                    onClick = { /* do something */ },
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
                } else {
                    LazyColumn {
                        items(20) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, start = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedCardWorkout(modifier = Modifier.weight(1f))
                                IconButton(
                                    onClick = { /* do something */ },
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

@Preview
@Composable
fun TrainingsWorkoutNamesEditScreenPreview() {
    BodyBalanceTheme {
        TrainingsWorkoutNamesEditScreen(false)
    }
}