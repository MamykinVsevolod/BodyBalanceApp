package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.iu6_mamykin.bodybalance.R
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.MyNavigationBar
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.OutlinedCard
import com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components.SegmentedButton
import com.iu6_mamykin.bodybalance.ui.theme.BodyBalanceTheme
import com.iu6_mamykin.bodybalance.ui.theme.GreenColor
import com.iu6_mamykin.bodybalance.ui.theme.WhiteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen() {
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
                onClick = { /* do something */ },
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
        bottomBar = { MyNavigationBar(0) }
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
                SegmentedButton()
                LazyColumn {
                    items(20) {
                        OutlinedCard()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TrainingScreenPreview() {
    BodyBalanceTheme {
        TrainingScreen()
    }
}