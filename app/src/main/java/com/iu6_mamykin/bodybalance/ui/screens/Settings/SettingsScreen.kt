package com.iu6_mamykin.bodybalance.ui.screens.Settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.screens.Settings.components.OutlinedCardTrainingsSettings
import com.iu6_mamykin.bodybalance.navigation.MyNavigationBar
import com.iu6_mamykin.bodybalance.ui.theme.BlackColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text("Настройки")
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
                OutlinedCardTrainingsSettings(navController, "Названия тренировок", true)
                OutlinedCardTrainingsSettings(navController, "Названия упражнений", false)
            }
        }
    }
}

/*
@Preview
@Composable
fun SettingsScreenPreview() {
    BodyBalanceTheme {
        SettingsScreen(navController: NavController)
    }
}*/
